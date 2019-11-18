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
@Api(tags="【3】部门的数据处理")
public class StationDealController {
	@Autowired
	private StationDealService stationDealService;
	
	@GetMapping("1/excel/add")
	@ApiOperation("（1）对比找到新增数据，以orgcode为准，空数据不管")
	public void getAddDifferenceExcel(HttpServletResponse response) {
		stationDealService.getAddDifferenceExcel(response);
	}
	@GetMapping("2/excel/repeatandnull")
	@ApiOperation("（2）对比找到部门组织机构为空的数据、或者我们系统已经存在的数据")
	public void getRepeatForExcel(HttpServletResponse response) {
		stationDealService.getRepeatForExcel(response);
	}
	@GetMapping("3/deal/deleteRepeatData")
	@ApiOperation("（3）新增数据确实是待处理数据，直接删除")
	public void getAddDifferenceExcel() {
		stationDealService.deleteRepeatData();
	}
	@ApiOperation("（4）对比找到减少的数据，以orgcode为准，空数据不管。目前这个知道即可无需处理。")
	@GetMapping("4/excel/delete")
	public void getDeleteDifferenceExcel(HttpServletResponse response) {
		stationDealService.getDeleteDifferenceExcel(response);
	}
	@ApiOperation("新增数据处理父id")
	@GetMapping("difference/parentid")
	public void dealParentId() {
		stationDealService.dealParentId();
	}
	
	
	
	
	@ApiOperation("新增的部分生成sql语句[(1)type=0是我们的id父id同第三方厂家，直接检查新增父id是否在系统存在，存在就插入]")
	@GetMapping("difference/sqlforadd")
	public String sqlForAdd(@ApiParam("使用uuid，true表示使用，false表示不用，默认使用") @RequestParam("uuid") Boolean uuid,
			@ApiParam("type=1表示父id为orgcode，type=0表示自带部门id")	@RequestParam("type") 	Integer type 
			) {
		if(type>1) {
			throw new RuntimeException("type只能取值0或者1");
		}
		return stationDealService.sqlForAdd(uuid,type);
	}
	
	@GetMapping("99/createsql")
	@ApiOperation("(99)数据检查：部门id不能为空。最后一步，将临时表数据库的部门数据生成sql，实现开发、测试、生产环境的通用。")
	public String createSql() {
		return stationDealService.createSql();
	}
	
}
