package com.yjy.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yjy.mapper.user_our.UserOurMapper;
import com.yjy.model.PoliceModel;
import com.yjy.model.StationModel;
import com.yjy.service.UserDealService;
import com.yjy.util.ExcelTemplateExporter;
import com.yjy.util.SqlCreate;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import lombok.extern.slf4j.Slf4j;

import com.yjy.config.InitConfig;
import com.yjy.mapper.user_other.UserOtherMapper;
@Service
@Slf4j
public class UserDealServiceImp implements UserDealService{
	@Autowired
	private UserOurMapper userOurMapper;
	@Autowired
	private UserOtherMapper userOtherMapper;
	@Autowired
	private InitConfig config;
	@Autowired
	private ExcelTemplateExporter excelTemplateExporter;
	String sql=
			"insert into T_POLICE_INFO"
			+ "(ID,POLICE_CODE,NAME,STATIONID,GENDER,TELEPHONE,POLICE_POSITION,RADIO_ID) "
			+ "values(#{id},#{policeCode},#{policeName},#{otherStationId},#{sex},#{phone},#{policePosition},#{radio},#{officeTel});"
			+ "insert into T_PRIV_USER"
			+ "(ID,USERNAME,LOGINNAME,PWD,STATE,POLICE_GUID)"
			+ "values(#{userId},#{policeName},#{policeCode},#{policeCode},'1',#{id})";

	
	
	
	private Map<String,StationModel> getStation(){
		List<StationModel> stationList=userOurMapper.findAllStation();
		Map<String,StationModel> stationMap=new HashMap<String,StationModel>();
		for(StationModel stationModel:stationList) {
			String orgcode = stationModel.getOrgcode();
			if(StringUtils.isEmpty(orgcode)) {
				log.error("orgcode为空{}",orgcode);
				continue;
			}
			stationMap.put(orgcode, stationModel);
		}
		return stationMap;
	}

	
	public List<PoliceModel> getDifference(int type) {
		List<PoliceModel> userOurList = userOurMapper.findAllPolice();
		Map<String, PoliceModel> policeListToMap = policeListToMap(userOurList);

		List<PoliceModel> userOtherList = userOtherMapper.findAllPolice();
		Map<String,PoliceModel> userOtherMap=policeListToMap(userOtherList);
		
		List<PoliceModel> needAddList=new ArrayList<PoliceModel>();
		List<PoliceModel> allHaveList=new ArrayList<PoliceModel>();
		List<PoliceModel> needDeleteList=new ArrayList<PoliceModel>();
		if(type==1) {
			for(PoliceModel policeModel:userOtherList) {
				String policeCode = policeModel.getPoliceCode();
				if(!StringUtils.isEmpty(policeCode)&&!policeListToMap.containsKey(policeCode)) {
					needAddList.add(policeModel);
				}
			}
			return needAddList;
		}
		
		if(10==type) {
			for(PoliceModel policeModel:userOtherList) {
				String policeCode = policeModel.getPoliceCode();
				 if(StringUtils.isEmpty(policeCode)||policeListToMap.containsKey(policeCode)) {
					allHaveList.add(policeModel);
				}
			}
			
			return allHaveList;
		}
		for(PoliceModel policeModel:userOurList) {
			String policeCode = policeModel.getPoliceCode();
			if(!StringUtils.isEmpty(policeCode)&&!userOtherMap.containsKey(policeCode)) {
				needDeleteList.add(policeModel);
			}
		}
		return needDeleteList;
		
	}

	/**
	 * @param userOurList
	 * @return
	 */
	private Map<String,PoliceModel>  policeListToMap(List<PoliceModel> list) {
		Map<String,PoliceModel> resultMap=new  HashMap<String, PoliceModel>();
		for(PoliceModel policeModel:list) {
			String policeCode = policeModel.getPoliceCode();
			if(!StringUtils.isEmpty(policeCode)) {
				resultMap.put(policeCode, policeModel);
			}
		}
		return resultMap;
	}


	public void getAddDifferenceExcel(HttpServletResponse response) {
		List<PoliceModel> difference = getDifference(1);
		excelTemplateExporter.exportExcel(difference, "新增的警员", "警员", PoliceModel.class, "新增的警员.xls", response);
		
	}


	public void getDeleteDifferenceExcel(HttpServletResponse response) {
		List<PoliceModel> difference = getDifference(0);
		excelTemplateExporter.exportExcel(difference, "我们系统有第三方厂家没有的警员", "我们系统有第三方厂家没有的警员", PoliceModel.class, "我们系统有第三方厂家没有的警员.xls", response);
	}

	public String createSql() {
		List<PoliceModel> findAllPolice = userOtherMapper.findAllPolice();
		for(int i=0;i<findAllPolice.size();i++) {
			PoliceModel policeModel = findAllPolice.get(i);
			policeModel.setSort(i+100);
			policeModel.setUserId(UUID.randomUUID().toString());
			String policeName = policeModel.getPoliceName();
			String policeCode = policeModel.getPoliceCode();
			if(StringUtils.isEmpty(policeName)) {
				throw new RuntimeException("警员name为空，请检查数据："+policeModel.toString());
			}
			if(StringUtils.isEmpty(policeCode)) {
				throw new RuntimeException("警员code为空，请检查数据："+policeModel.toString());
			}
		}
		String result=null;
		try {
			result = SqlCreate.getSqlForList(sql, findAllPolice);
		} catch (Exception e) {
			result="转sql失败";
			e.printStackTrace();
		} 
		return result;
	}

	public void getRepeatForExcel(HttpServletResponse response) {
		List<PoliceModel> difference = getDifference(10);
		excelTemplateExporter.exportExcel(difference, "警号为空或者系统已有的警员", "警号为空或者系统已有的警员", PoliceModel.class, "警号为空或者系统已有的警员.xls", response);
		
	}

	public void deleteRepeatData() {
		List<PoliceModel> difference = getDifference(10);
		for(PoliceModel policeModel:difference) {
			userOtherMapper.deleteById(policeModel.getId());
		}
		
	}

	public void dealSex() {
		Map<String, String> sexMap = config.getSexMap();
		for(String key:sexMap.keySet()) {
			String value = sexMap.get(key);
			userOtherMapper.updateSex(key,value);
		}
		
		
	}

	
}


