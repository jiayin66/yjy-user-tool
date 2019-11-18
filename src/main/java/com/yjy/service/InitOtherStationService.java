package com.yjy.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

public interface InitOtherStationService {

	String getCreateSql();

	void getNull(HttpServletResponse response);

	void excleImport(MultipartFile station);

}
