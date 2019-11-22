package com.yjy.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yjy.mapper.user_other.StationOtherMapper;
import com.yjy.mapper.user_other.UserOtherMapper;
import com.yjy.mapper.user_our.UserOurMapper;
import com.yjy.model.PoliceModel;
import com.yjy.model.StationModel;
import com.yjy.service.imp.UserDealServiceImp;
import com.yjy.util.SqlCreate;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/shantou/")
@Api(tags="【9】初始化第三方用户的数据")
@Slf4j
public class ShantouController {
	@Autowired
	private StationOtherMapper stationOtherMapper;
	@Autowired
	private UserOurMapper userOurMapper;
	@Autowired
	private UserOtherMapper userOtherMapper;
	
	@ApiOperation("(1) 经纬度")
	@GetMapping("1")
	public void dealLon() {
		List<StationModel> findAllStation = userOurMapper.findAllStation();
		Map<String,StationModel> map=new HashMap<String,StationModel>();
		for(StationModel stationModel:findAllStation) {
			if(StringUtils.isEmpty(stationModel.getOrgcode())) {
				continue;
			}
			map.put(stationModel.getOrgcode(), stationModel);
		}
		List<StationModel> findAllStation2 = stationOtherMapper.findAllStation();
		for(StationModel StationModel:findAllStation2) {
			String orgcode = StationModel.getOrgcode();
			if(!StringUtils.isEmpty(orgcode)) {
				if(map.containsKey(orgcode)) {
					StationModel stationModel2 = map.get(orgcode);
					String y = stationModel2.getLatitude();
					String x = stationModel2.getLongitude();
					String stationNum = stationModel2.getStationNum();
					stationOtherMapper.updateXY(stationNum,x, y, orgcode);
				}
			}
		}
	}
	@ApiOperation("(3) 修改为空的手机号")
	@GetMapping("3")
	public void dealnullphone() {
		//我们的警员组成map
		List<PoliceModel> ourPolice = userOurMapper.findAllPolice();
		Pattern pattern = Pattern.compile("^\\d+$");
		for(PoliceModel policeModel:ourPolice) {
			String phone = policeModel.getPhone();
			if(!StringUtils.isEmpty(phone)) {
				continue;
			}
			String policeCode = policeModel.getPoliceCode();
			if(StringUtils.isEmpty(policeCode)) {
				continue;
			}
			Matcher matcher = pattern.matcher(policeCode);
			if(matcher.find()) {
				if(policeCode.length()>4) {
					String subcode = policeCode.substring(policeCode.length()-4, policeCode.length());
					userOurMapper.updatePhone(policeModel.getId(),"1889885"+subcode);
				}
			}
		}
	}
	
	
	
	@ApiOperation("(2) 部门移动对警员的影响")
	@GetMapping("2")
	public void dealpolicesstation() {
		//新增的警员
		List<PoliceModel> addPolice=new ArrayList<PoliceModel>();
		//已存在的警员需要更新
		List<PoliceModel> needUpdatePolice=new ArrayList<PoliceModel>();
		
		List<PoliceModel> otherPolice = userOtherMapper.findAllPolice();
		
		//我们的警员组成map
		List<PoliceModel> ourPolice = userOurMapper.findAllPolice();
		Map<String,PoliceModel> ourPoliceMap=new HashMap<String,PoliceModel>();
		for(PoliceModel policeModel:ourPolice) {
			String policeCode = policeModel.getPoliceCode();
			if(!StringUtils.isEmpty(policeCode)) {
				ourPoliceMap.put(policeCode, policeModel);
			}
		}
		
		for(PoliceModel policeModel:otherPolice) {
			String policeCode = policeModel.getPoliceCode();
			//【1】他们中警号为空的我们不管
			if(StringUtils.isEmpty(policeCode)) {
				continue;
			}
			//【2】我们不包含的警号新增
			if(!ourPoliceMap.containsKey(policeCode)) {
				//警号发生变变化6位5位 就是警号的后4位在数据库能查到。
				//警号后4位相同，姓名相同，部门相同，更新警号
				//【3】警号错误的部分也做修改
				if(getMatch(policeCode)) {
					String subcode = policeCode.substring(policeCode.length()-4, policeCode.length());
					String policeName = policeModel.getPoliceName();
					if(StringUtils.isEmpty(policeName)) {
						continue;
					}
					List<PoliceModel> list=userOurMapper.findByCodeAndName(subcode,policeName);
					if(!CollectionUtils.isEmpty(list)) {
						if(list.size()>1) {
							throw new RuntimeException("subcode:"+subcode+"policeName"+policeName);
						}
						PoliceModel policeModel2 = list.get(0);
						policeModel.setId(policeModel2.getId());
						needUpdatePolice.add(policeModel);
					}
				}
				addPolice.add(policeModel);
				continue;
			}
			//【4】包含的部分修改属性
			PoliceModel ourModel = ourPoliceMap.get(policeCode);
			policeModel.setId(ourModel.getId());
			needUpdatePolice.add(policeModel);
		}
		
//		执行修改
		/*for(PoliceModel policeModel:needUpdatePolice) {
			userOurMapper.update(policeModel);
		}*/
		
		for(PoliceModel policeModel:addPolice) {
			policeModel.setId(UUID.randomUUID().toString());
			policeModel.setUserId(UUID.randomUUID().toString());
		}
		
		
		String result=null;
		try {
			result = SqlCreate.getSqlForList(UserDealServiceImp.sql, addPolice);
		} catch (Exception e) {
			result="转sql失败";
			e.printStackTrace();
		} 
		log.error(result);

	}
	
	/**
	 * 全数字，而且大于4位
	 * @param policeCode
	 * @return
	 */
	private boolean getMatch(String policeCode) {
		Pattern pattern = Pattern.compile("^\\d+$");
		Matcher matcher = pattern.matcher(policeCode);
		if(matcher.find()) {
			if(policeCode.length()>4) {
				return true;
			}
		}
		return false;
	}
	
}
