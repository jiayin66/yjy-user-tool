package com.yjy.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.yjy.mapper.user_our.UserOurMapper;
import com.yjy.model.PoliceModel;
import com.yjy.model.StationModel;
import com.yjy.service.StationDealService;
import com.yjy.service.UserDealService;
import com.yjy.util.ExcelTemplateExporter;
import com.yjy.util.SqlCreate;

import ch.qos.logback.core.pattern.color.BlackCompositeConverter;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import lombok.extern.slf4j.Slf4j;

import com.yjy.config.InitConfig;
import com.yjy.mapper.user_other.StationOtherMapper;
import com.yjy.mapper.user_other.UserOtherMapper;
@Service
@Slf4j
public class StationDealServiceImp implements StationDealService{
	@Autowired
	private UserOurMapper userOurMapper;
	@Autowired
	private UserOtherMapper UserOtherMapper;
	@Autowired
	private StationOtherMapper stationOtherMapper;
	@Autowired
	private ExcelTemplateExporter excelTemplateExporter;
	@Autowired
	private InitConfig config;
	String sql=
			"insert into T_POLICE_STATION_INFO"
			+ "(ID,PARENTID,STATIONNAME,ORGCODE,STATIONNUM,LONGITUDE,LATITUDE,TEL,ADDRESS,RADIO_ID) "
			+ "values(#{id},#{parentId},#{stationName},#{orgcode},#{stationNum},#{longitude},#{latitude},#{tel},#{address},#{radioId})";

	
	/**
	 * @return
	 */
	private Map<String,StationModel> getOrgcodeStationMap(){
		List<StationModel> stationList=userOurMapper.findAllStation();
		Map<String,StationModel> stationMap=new HashMap<String,StationModel>();
		for(StationModel stationModel:stationList) {
			String orgcode = stationModel.getOrgcode();
			if(StringUtils.isEmpty(orgcode)) {
				log.error("orgcode????{}",orgcode);
				continue;
			}
			stationMap.put(orgcode, stationModel);
		}
		return stationMap;
	}

	/**
	 * @param type
	 * @return
	 */
	public List<StationModel> getDifference(int type) {
		List<StationModel> stationOurList = userOurMapper.findAllStation();
		Map<String, StationModel> stationListToMap = stationListToMap(stationOurList);

		List<StationModel> stationOtherList = stationOtherMapper.findAllStation();
		Map<String,StationModel> stationOtherMap=stationListToMap(stationOtherList);
		
		List<StationModel> needAddList=new ArrayList<StationModel>();
		List<StationModel> allHaveList=new ArrayList<StationModel>();
		List<StationModel> needDeleteList=new ArrayList<StationModel>();
		if(type==1) {
			for(StationModel stationModel:stationOtherList) {
				String orgcode = stationModel.getOrgcode();
				if(!StringUtils.isEmpty(orgcode)&&!stationListToMap.containsKey(orgcode)) {
					needAddList.add(stationModel);
				}
			}
			return needAddList;
		}
		if(10==type) {
			for(StationModel stationModel:stationOtherList) {
				String orgcode = stationModel.getOrgcode();
				 if(StringUtils.isEmpty(orgcode)||stationListToMap.containsKey(orgcode)) {
					allHaveList.add(stationModel);
				}
			}
			
			return allHaveList;
		}
		for(StationModel stationModel:stationOurList) {
			String orgcode = stationModel.getOrgcode();
			if(!StringUtils.isEmpty(orgcode)&&!stationOtherMap.containsKey(orgcode)) {
				needDeleteList.add(stationModel);
			}
		}
		return needDeleteList;
		
	}

	/**
	 * @param userOurList
	 * @return
	 */
	private Map<String,StationModel>  stationListToMap(List<StationModel> list) {
		Map<String,StationModel> resultMap=new  HashMap<String, StationModel>();
		for(StationModel stationModel:list) {
			String orgcode = stationModel.getOrgcode();
			if(!StringUtils.isEmpty(orgcode)) {
				resultMap.put(orgcode, stationModel);
			}
		}
		return resultMap;
	}


	public void getAddDifferenceExcel(HttpServletResponse response) {
		List<StationModel> difference = getDifference(1);
		excelTemplateExporter.exportExcel(difference, "新增的部门", "新增的部门", StationModel.class, "新增的部门.xls", response);
		
	}


	public void getDeleteDifferenceExcel(HttpServletResponse response) {
		List<StationModel> difference = getDifference(0);
		excelTemplateExporter.exportExcel(difference, "我们有第三方没有的部门", "我们有第三方没有的部门", StationModel.class, "我们有第三方没有的部门.xls", response);
	}

	public String sqlForAdd(Boolean uuid,Integer type) {
		List<StationModel> difference = getDifference(1);
		if(CollectionUtils.isEmpty(difference)) {
			return "临时部门表中无数据，无法生成sql！";
		}
		//orgcode
		if(type==1) {
			return	addToDatabaseParentOrgcode(uuid,difference);
		}else {
			String result=null;
			try {
				result=  addToDatabaseParentId(uuid,difference);
			} catch (Exception e) {
				result="转sql失败";
				e.printStackTrace();
			} 
			return result;
		}
	}
	public String addToDatabaseParentOrgcode(Boolean uuid,List<StationModel> difference) {
		return null;
	}
	/**
	 * @param uuid
	 * @param difference
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 */
	public String addToDatabaseParentId(Boolean uuid,List<StationModel> difference) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		List<StationModel> findAllStation = stationOtherMapper.findAllStation();
		Map<String,StationModel>  parentMap=new HashMap<String, StationModel>();
		for(StationModel stationModel:findAllStation) {
			parentMap.put(stationModel.getParentId(), stationModel);
		}
		List<StationModel> result=new ArrayList<StationModel>();
		for(StationModel stationModel:difference) {
			String parentId = stationModel.getParentId();
			if(!StringUtils.isEmpty(parentId)&&parentMap.containsKey(parentId)) {
				result.add(stationModel);
			}
		}
		if(CollectionUtils.isEmpty(result)) {
			return "无数据";
		}
		
		return SqlCreate.getSqlForList(sql, result);
	}

	public String createSql() {
		List<StationModel> findAllStation = stationOtherMapper.findAllStation();
		String result;
		try {
			result = SqlCreate.getSqlForList(sql, findAllStation);
		} catch (Exception e) {
			result="失败";
			e.printStackTrace();
		} 
		return result;

	}

	public void deleteRepeatData() {
		List<StationModel> difference = getDifference(10);
		for(StationModel stationModel:difference) {
			stationOtherMapper.deleteById(stationModel.getId());
		}
	}

	
	public void getRepeatForExcel(HttpServletResponse response) {
		List<StationModel> difference = getDifference(10);
		excelTemplateExporter.exportExcel(difference, "部门orgcode为空或者我们已经存在的部门", "部门orgcode为空或者我们已经存在的部门", StationModel.class, "部门orgcode为空或者我们已经存在的部门.xls", response);
	}

	public void dealParentId() {
		Map<String, StationModel> orgcodeStationMap = getOrgcodeStationMap();
		List<StationModel> findAllStation = stationOtherMapper.findAllStation();
		List<StationModel> listResult=new ArrayList<StationModel>();
		for(StationModel stationModel:findAllStation) {
			String orgcode = stationModel.getOrgcode();
			if(!StringUtils.isEmpty(orgcode)&& orgcodeStationMap.containsKey(orgcode)) {
				stationModel.setParentId(orgcodeStationMap.get(orgcode).getId());
				listResult.add(stationModel);
			}
			
		}
		updateBatch(listResult);
	}
	
	private void updateBatch(List<StationModel> listResult){
		for(StationModel stationModel:listResult) {
			stationOtherMapper.update(stationModel);
		}
		
	}

	
	
	


	
}


