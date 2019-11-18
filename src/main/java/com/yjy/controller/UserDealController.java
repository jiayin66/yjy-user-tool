package com.yjy.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yjy.model.PoliceModel;
import com.yjy.model.SexModel;
import com.yjy.service.UserDealService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/user/")
@Api(tags="��4���û������ݴ���")
public class UserDealController {
	@Autowired
	private UserDealService dataDealService;
	
	@GetMapping("1/difference/add")
	@ApiOperation("(1)excel�����������ݣ���policeCodeΪ׼�������ݲ���")
	public void getAddDifferenceExcel(HttpServletResponse response) {
		dataDealService.getAddDifferenceExcel(response);
	}
	@GetMapping("2/1/excel/repeatandnull")
	@ApiOperation("��2.1���Ա��ҵ�policeCodeΪ�ա���������ϵͳ�Ѿ����ڵ�����")
	public void getRepeatForExcel(HttpServletResponse response) {
		dataDealService.getRepeatForExcel(response);
	}
	@ApiOperation("(2.2)Ŀǰ����ܡ����Ǳȵ��������Ҷ�����ݣ���policeCodeΪ׼�������ݲ���")
	@GetMapping("2/2/excel/delete")
	public void getDeleteDifferenceExcel(HttpServletResponse response) {
		dataDealService.getDeleteDifferenceExcel(response);
	}
	@GetMapping("3/deal/deleteRepeatData")
	@ApiOperation("��3����������ȷʵ�Ǵ��������ݣ�ֱ��ɾ��")
	public void getAddDifferenceExcel() {
		dataDealService.deleteRepeatData();
	}
	@PostMapping("4/deal/sex")
	@ApiOperation("��4���Ա�ת���������ļ���д�Ա�ת��eg����->1")
	public void dealSex(@RequestBody List<SexModel> sexModelList) {
		dataDealService.dealSex(sexModelList);
	}
	@GetMapping("5/station/change")
	@ApiOperation("��5�����ŵ�idת������Դ�ǲ���id�����ŵ�orgcode�����ŵ����֣�")
	public String stationDealForId(@ApiParam("1��idת����2��orgcodeת����3�ǲ�����ת��") @RequestParam("type") Integer type) {
		return dataDealService.stationDealForId(type);
	}
	
	
	
	@GetMapping("99/createsql")
	@ApiOperation("(99)���ݼ�飺��Ҫ���ţ���������Ϊ�ա�֮�����һ��������ʱ�����ݿ�Ĳ�����������sql��ʵ�ֿ��������ԡ�����������ͨ�á�userIdÿ��������ɡ���")
	public String createSql() {
		return dataDealService.createSql();
	}
	
}
