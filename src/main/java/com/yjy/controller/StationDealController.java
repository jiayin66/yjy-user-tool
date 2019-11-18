package com.yjy.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yjy.model.PoliceModel;
import com.yjy.service.StationDealService;
import com.yjy.service.UserDealService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/station/")
@Api(tags="��3�����ŵ����ݴ���")
public class StationDealController {
	@Autowired
	private StationDealService stationDealService;
	
	@GetMapping("1/excel/add")
	@ApiOperation("��1���Ա��ҵ��������ݣ���orgcodeΪ׼�������ݲ���")
	public void getAddDifferenceExcel(HttpServletResponse response) {
		stationDealService.getAddDifferenceExcel(response);
	}
	@GetMapping("2/excel/repeatandnull")
	@ApiOperation("��2���Ա��ҵ�������֯����Ϊ�յ����ݡ���������ϵͳ�Ѿ����ڵ�����")
	public void getRepeatForExcel(HttpServletResponse response) {
		stationDealService.getRepeatForExcel(response);
	}
	@GetMapping("3/deal/deleteRepeatData")
	@ApiOperation("��3����������ȷʵ�Ǵ��������ݣ�ֱ��ɾ��")
	public void getAddDifferenceExcel() {
		stationDealService.deleteRepeatData();
	}
	@ApiOperation("��4���Ա��ҵ����ٵ����ݣ���orgcodeΪ׼�������ݲ��ܡ�Ŀǰ���֪���������账��")
	@GetMapping("4/excel/delete")
	public void getDeleteDifferenceExcel(HttpServletResponse response) {
		stationDealService.getDeleteDifferenceExcel(response);
	}
	@ApiOperation("�������ݴ���id")
	@GetMapping("difference/parentid")
	public void dealParentId() {
		stationDealService.dealParentId();
	}
	
	
	
	
	@ApiOperation("�����Ĳ�������sql���[(1)type=0�����ǵ�id��idͬ���������ң�ֱ�Ӽ��������id�Ƿ���ϵͳ���ڣ����ھͲ���]")
	@GetMapping("difference/sqlforadd")
	public String sqlForAdd(@ApiParam("ʹ��uuid��true��ʾʹ�ã�false��ʾ���ã�Ĭ��ʹ��") @RequestParam("uuid") Boolean uuid,
			@ApiParam("type=1��ʾ��idΪorgcode��type=0��ʾ�Դ�����id")	@RequestParam("type") 	Integer type 
			) {
		if(type>1) {
			throw new RuntimeException("typeֻ��ȡֵ0����1");
		}
		return stationDealService.sqlForAdd(uuid,type);
	}
	
	@GetMapping("99/createsql")
	@ApiOperation("(99)���ݼ�飺����id����Ϊ�ա����һ��������ʱ�����ݿ�Ĳ�����������sql��ʵ�ֿ��������ԡ�����������ͨ�á�")
	public String createSql() {
		return stationDealService.createSql();
	}
	
}
