package com.yjy.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.yjy.model.PoliceModel;
import com.yjy.model.SexModel;

public interface UserDealService {

	void getAddDifferenceExcel(HttpServletResponse response);

	void getDeleteDifferenceExcel(HttpServletResponse response);

	String createSql();

	void getRepeatForExcel(HttpServletResponse response);

	void deleteRepeatData();

	void dealSex(List<SexModel> sexModelList);

	String stationDealForId(Integer type);

	

}
