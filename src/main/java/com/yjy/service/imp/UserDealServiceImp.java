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
	public static String sql=
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
				log.error("orgcodeΪ��{}",orgcode);
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
		excelTemplateExporter.exportExcel(difference, "�����ľ�Ա", "��Ա", PoliceModel.class, "�����ľ�Ա.xls", response);
		
	}


	public void getDeleteDifferenceExcel(HttpServletResponse response) {
		List<PoliceModel> difference = getDifference(0);
		excelTemplateExporter.exportExcel(difference, "����ϵͳ�е���������û�еľ�Ա", "����ϵͳ�е���������û�еľ�Ա", PoliceModel.class, "����ϵͳ�е���������û�еľ�Ա.xls", response);
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
				throw new RuntimeException("��ԱnameΪ�գ��������ݣ�"+policeModel.toString());
			}
			if(StringUtils.isEmpty(policeCode)) {
				throw new RuntimeException("��ԱcodeΪ�գ��������ݣ�"+policeModel.toString());
			}
		}
		String result=null;
		try {
			result = SqlCreate.getSqlForList(sql, findAllPolice);
		} catch (Exception e) {
			result="תsqlʧ��";
			e.printStackTrace();
		} 
		return result;
	}

	public void getRepeatForExcel(HttpServletResponse response) {
		List<PoliceModel> difference = getDifference(10);
		excelTemplateExporter.exportExcel(difference, "����Ϊ�ջ���ϵͳ���еľ�Ա", "����Ϊ�ջ���ϵͳ���еľ�Ա", PoliceModel.class, "����Ϊ�ջ���ϵͳ���еľ�Ա.xls", response);
		
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
		//1.�õ�ȫ���Ĵ�ת��������Ϣ
		List<String> staionList=userOtherMapper.findForStationId(type);
		if(CollectionUtils.isEmpty(staionList)) {
			return "�������Ͳ�ѯ����Ӧ������Ϊ��,�����Ƿ��������";
		}
		//2.�õ����ǲ��ŵ�ӳ���ϵ
		List<StationModel> ourStationList = userOurMapper.findAllStation();
		//����ת��4����
		Map<String,String> ourStaListMap=new HashMap<String,String>();
		//����ת��123���ͣ�orgcode��Ϊý�飩
		Map<String,String> ourOrgcodeToIdMap=new HashMap<String,String>();
		for(StationModel stationModel:ourStationList) {
			ourStaListMap.put(stationModel.getId(), stationModel.getId());
			String orgcode = stationModel.getOrgcode();
			if(StringUtils.isEmpty(orgcode)) {
				//���ǵ�orgcodeΪ�վ�û���ҵ����ݣ���ʱ���衣
				continue;
			}
			ourOrgcodeToIdMap.put(stationModel.getOrgcode(), stationModel.getId());
		}
		//3.�õ����ǵ�ת��
		Map<String, String> idToOrgcodeMap=new HashMap<String, String>();
		Map<String, String> nameToOrgcodeMap=new HashMap<String, String>();
		List<StationModel> findAllStation = stationOtherMapper.findAllStation();
		for(StationModel stationModel:findAllStation) {
			String orgcode = stationModel.getOrgcode();
			if(StringUtils.isEmpty(orgcode)) {
				//���ǵ�orgcodeΪ�գ���ʱ�޷�ͨ�����ַ�ʽת��
				log.error("���������ҵĸò���û��orgcode����ʱ�޷�ͨ��orgcode��ת����{}",stationModel.toString());
				continue;
			}
			idToOrgcodeMap.put(stationModel.getId(),orgcode );
			nameToOrgcodeMap.put(stationModel.getStationName(), orgcode);
		}
		//1�ǳ��Ҹ����ǵ�id�������ݿ���Լ�id��orgcode����2�ǳ��Ҹ�orgcode��orgcode����3�ǳ��Ҹ���������orgcode����4�ǳ��Ҹ�����idͬʱ�������ݿ�����ǵ�id
		switch (type) {
		case 1:
			for(String s:staionList) {
				if(!idToOrgcodeMap.containsKey(s)) {
					//˵���Ҳ���ӳ�䣬���߲��ŵ�orgcodeΪ��
					log.info("��Ա�Ĳ���id�Ҳ��������ṩ��orgcode�����߲��ŵ�orgcodeΪ��");
					continue;
				}
				String orgcode = idToOrgcodeMap.get(s);
				if(StringUtils.isEmpty(orgcode)) {
					throw new RuntimeException("ǰ���Ѿ�ȥ���˿յ�id-orgcode�������������������������˵���쳣");
				}
				String ourId = ourOrgcodeToIdMap.get(orgcode);
				if(StringUtils.isEmpty(ourId)) {
					log.error("���ǲ�����û�������֯�������뵼��ӳ��ʧ��:"+orgcode+",��������"+s);
				}
				userOtherMapper.updateStationId(s,ourId,type);
			}
			return "ת���ɹ�";
			
		case 2:
			for(String s:staionList) {
				String ourId = ourOrgcodeToIdMap.get(s);
				if(StringUtils.isEmpty(ourId)) {
					throw new RuntimeException("���ǲ�����û�������֯��������:"+s);
				}
				userOtherMapper.updateStationId(s,ourId,type);
			}
			return "ת���ɹ�";
			
		case 3:
			for(String s:staionList) {
				if(!nameToOrgcodeMap.containsKey(s)) {
					throw new RuntimeException("�ڵ������Ĳ������Ҳ��������������"+s) ;
				}
				String orgcode = nameToOrgcodeMap.get(s);
				if(StringUtils.isEmpty(orgcode)) {
					throw new RuntimeException("�ڵ������Ĳ���û��orgcode:"+orgcode+",��������"+s);
				}
				String ourId = ourOrgcodeToIdMap.get(orgcode);
				if(StringUtils.isEmpty(ourId)) {
					throw new RuntimeException("���ǲ�����û�������֯��������:"+orgcode+",��������"+s);
				}
				userOtherMapper.updateStationId(s,ourId,type);
			}
			return "ת���ɹ�";
		case 4:
			for(String s:staionList) {
				if(ourStaListMap.containsKey(s)) {
					userOtherMapper.updateStationId(s,s,type);
				}
			}
			
			return "ת���ɹ�";
		default:
			break;
		}
		
		return "���������������";
	}
	
}


