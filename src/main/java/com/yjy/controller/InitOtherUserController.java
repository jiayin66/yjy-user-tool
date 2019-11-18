package com.yjy.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yjy.service.InitOtherUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/init/user/")
@Api(tags="【2】初始化第三方用户的数据")
public class InitOtherUserController {
	@Autowired
	private InitOtherUserService initOtherUserService;
	
	@ApiOperation("（1）返回建表语句")
	@GetMapping("1/sql")
	public String getCreateSql() {
		return initOtherUserService.getCreateSql();
	}
	@ApiOperation("(2) 获取模板")
	@GetMapping("2/excel/getnull")
	public void getNull(HttpServletResponse response) {
		initOtherUserService.getNull(response);
	}
	@ApiOperation("(3) 数据的导入,清空历史数据")
	@PostMapping("3/excel/import")
	public void excleImport(@RequestParam("police") MultipartFile police) {
		initOtherUserService.excleImport(police);
	}
}
