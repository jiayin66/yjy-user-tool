package com.yjy.service.imp;

import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.yjy.mapper.user_our.UserOurMapper;
import com.yjy.model.PoliceModel;
import com.yjy.model.SexModel;
import com.yjy.model.StationModel;
import com.yjy.service.UserDealService;
import com.yjy.util.ExcelTemplateExporter;
import com.yjy.util.SqlCreate;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import lombok.extern.slf4j.Slf4j;

import com.yjy.config.InitConfig;
import com.yjy.mapper.user_other.StationOtherMapper;
import com.yjy.mapper.user_other.UserOtherMapper;
@Service
@Slf4j
public class UserDealServiceImp implements UserDealService{
	@Autowired
	private UserOurMapper userOurMapper;
	@Autowired
	private UserOtherMapper userOtherMapper;
	@Autowired
	private StationOtherMapper stationOtherMapper;
	@Autowired
	private InitConfig config;
	@Autowired
	private ExcelTemplateExporter excelTemplateExporter;
	String sql=
			"insert into T_POLICE_INFO"
			+ "(ID,POLICE_CODE,NAME,STATIONID,GENDER,TELEPHONE,POLICE_POSITION,RADIO_ID,OFFICE_TEL) "
			+ "values(#{id},#{policeCode},#{policeName},#{stationId},#{sex},#{phone},#{policePosition},#{radio},#{officeTel});"
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

	public void dealSex(List<SexModel> sexModelList) {
		for(SexModel sexModel:sexModelList) {
			userOtherMapper.updateSex(sexModel.getOthercode(),sexModel.getOurcode());
		}
	}


	
	@Override
	public String stationDealForId(Integer type) {
		//1.拿到全部的待转换文字
		List<String> staionList=userOtherMapper.findForStationId(type);
		if(CollectionUtils.isEmpty(staionList)) {
			return "根据类型查询到对应的数据为空,请检查是否类型填错！";
		}
		//2.拿到我们orgcode转id映射
		List<StationModel> ourStationList = userOurMapper.findAllStation();
		Map<String,String> ourOrgcodeToIdMap=new HashMap<String,String>();
		for(StationModel stationModel:ourStationList) {
			ourOrgcodeToIdMap.put(stationModel.getOrgcode(), stationModel.getId());
		}
		//3.拿到他们的转换
		Map<String, String> idToOrgcodeMap=new HashMap<String, String>();
		Map<String, String> nameToOrgcodeMap=new HashMap<String, String>();
		List<StationModel> findAllStation = stationOtherMapper.findAllStation();
		for(StationModel stationModel:findAllStation) {
			idToOrgcodeMap.put(stationModel.getId(), stationModel.getOrgcode());
			nameToOrgcodeMap.put(stationModel.getStationName(), stationModel.getOrgcode());
		}
		//1是id转换，2是orgcode转换，3是部门名转换。
		switch (type) {
		case 1:
			for(String s:staionList) {
				if(!idToOrgcodeMap.containsKey(s)) {
					throw new RuntimeException("在第三方的部门中找不到这个id："+s) ;
				}
				String orgcode = idToOrgcodeMap.get(s);
				if(StringUtils.isEmpty(orgcode)) {
					throw new RuntimeException("在第三方的部门没有orgcode:"+orgcode+",第三方名"+s);
				}
				String ourId = ourOrgcodeToIdMap.get(orgcode);
				if(StringUtils.isEmpty(ourId)) {
					throw new RuntimeException("我们部门中没有这个组织机构代码:"+orgcode+",第三方名"+s);
				}
				userOtherMapper.updateStationId(s,ourId,type);
			}
			return "转换成功";
			
		case 2:
			for(String s:staionList) {
				String ourId = ourOrgcodeToIdMap.get(s);
				if(StringUtils.isEmpty(ourId)) {
					throw new RuntimeException("我们部门中没有这个组织机构代码:"+s);
				}
				userOtherMapper.updateStationId(s,ourId,type);
			}
			return "转换成功";
			
		case 3:
			for(String s:staionList) {
				if(!nameToOrgcodeMap.containsKey(s)) {
					throw new RuntimeException("在第三方的部门中找不到这个部门名："+s) ;
				}
				String orgcode = nameToOrgcodeMap.get(s);
				if(StringUtils.isEmpty(orgcode)) {
					throw new RuntimeException("在第三方的部门没有orgcode:"+orgcode+",第三方名"+s);
				}
				String ourId = ourOrgcodeToIdMap.get(orgcode);
				if(StringUtils.isEmpty(ourId)) {
					throw new RuntimeException("我们部门中没有这个组织机构代码:"+orgcode+",第三方名"+s);
				}
				userOtherMapper.updateStationId(s,ourId,type);
			}
			return "转换成功";
		default:
			break;
		}
		
		return "您输入的类型有误";
	}
	
}


