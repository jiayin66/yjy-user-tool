package com.yjy.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

public interface InitOtherUserService {

	String getCreateSql();
	
	void excleImport(MultipartFile police);
	
	void getNull(HttpServletResponse response);

}
