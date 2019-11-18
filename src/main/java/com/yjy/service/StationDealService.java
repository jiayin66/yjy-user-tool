package com.yjy.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.yjy.model.PoliceModel;

public interface StationDealService {

	void getAddDifferenceExcel(HttpServletResponse response);

	void getDeleteDifferenceExcel(HttpServletResponse response);

	String sqlForAdd(Boolean uuid,Integer type);

	String createSql();

	void deleteRepeatData();

	void getRepeatForExcel(HttpServletResponse response);

	void dealParentId();


	

}
