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
import com.yjy.service.UserDealService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/user/")
@Api(tags="【4】用户的数据处理")
public class UserDealController {
	@Autowired
	private UserDealService dataDealService;
	
	@GetMapping("1/difference/add")
	@ApiOperation("(1)excel导出新增数据，以policeCode为准，空数据不管")
	public void getAddDifferenceExcel(HttpServletResponse response) {
		dataDealService.getAddDifferenceExcel(response);
	}
	@GetMapping("2/1/excel/repeatandnull")
	@ApiOperation("（2.1）对比找到policeCode为空、或者我们系统已经存在的数据")
	public void getRepeatForExcel(HttpServletResponse response) {
		dataDealService.getRepeatForExcel(response);
	}
	@ApiOperation("(2.2)目前这项不管。我们比第三方厂家多的数据，以policeCode为准，空数据不管")
	@GetMapping("2/2/excel/delete")
	public void getDeleteDifferenceExcel(HttpServletResponse response) {
		dataDealService.getDeleteDifferenceExcel(response);
	}
	@GetMapping("3/deal/deleteRepeatData")
	@ApiOperation("（3）新增数据确实是待处理数据，直接删除")
	public void getAddDifferenceExcel() {
		dataDealService.deleteRepeatData();
	}
	@GetMapping("4/deal/sex")
	@ApiOperation("（4）性别转换。配置文件填写性别转换eg：男->1")
	public void dealSex() {
		dataDealService.dealSex();
	}
	
	
	
	@GetMapping("99/createsql")
	@ApiOperation("(99)数据检查：需要警号，姓名不能为空。之后最后一步，将临时表数据库的部门数据生成sql，实现开发、测试、生产环境的通用。userId每次随机生成。。")
	public String createSql() {
		return dataDealService.createSql();
	}
	
}
