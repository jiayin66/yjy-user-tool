package com.yjy.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yjy.service.InitOtherStationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/init/station/")
@Api(tags="【1】初始化第三方的部门数据")
public class InitOtherStationController {
	@Autowired
	private InitOtherStationService initOtherStationService;
	
	@ApiOperation("（1）返回建表语句")
	@GetMapping("1/sql")
	public String getCreateSql() {
		return initOtherStationService.getCreateSql();
	}
	
	@ApiOperation("(2) 获取模板")
	@GetMapping("2/excel/getnull")
	public void getNull(HttpServletResponse response) {
		initOtherStationService.getNull(response);
	}
	
	@ApiOperation("(3) 数据的导入")
	@PostMapping("3/excel/import")
	public void excleImport(@RequestParam("station") MultipartFile station) {
		initOtherStationService.excleImport(station);
	}
}
