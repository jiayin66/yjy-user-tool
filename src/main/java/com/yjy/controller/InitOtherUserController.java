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
@Api(tags="��2����ʼ���������û�������")
public class InitOtherUserController {
	@Autowired
	private InitOtherUserService initOtherUserService;
	
	@ApiOperation("��1�����ؽ������")
	@GetMapping("1/sql")
	public String getCreateSql() {
		return initOtherUserService.getCreateSql();
	}
	@ApiOperation("(2) ��ȡģ��")
	@GetMapping("2/excel/getnull")
	public void getNull(HttpServletResponse response) {
		initOtherUserService.getNull(response);
	}
	@ApiOperation("(3) ���ݵĵ���,�����ʷ����")
	@PostMapping("3/excel/import")
	public void excleImport(@RequestParam("police") MultipartFile police) {
		initOtherUserService.excleImport(police);
	}
}
