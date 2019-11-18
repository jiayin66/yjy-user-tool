package com.yjy.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.yjy.model.PoliceModel;

public interface UserDealService {

	void getAddDifferenceExcel(HttpServletResponse response);

	void getDeleteDifferenceExcel(HttpServletResponse response);

	String createSql();

	void getRepeatForExcel(HttpServletResponse response);

	void deleteRepeatData();

	void dealSex();

	

}
