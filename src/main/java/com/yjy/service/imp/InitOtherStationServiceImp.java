package com.yjy.service.imp;

import com.yjy.content.Sql;
import com.yjy.mapper.user_other.StationOtherMapper;
import com.yjy.model.StationModel;
import com.yjy.service.InitOtherStationService;
import com.yjy.util.ExcelTemplateExporter;
import com.yjy.util.MultipartUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
@Service
public class InitOtherStationServiceImp implements InitOtherStationService{
	@Autowired
	private ExcelTemplateExporter excelTemplateExporter;
	@Autowired
	private StationOtherMapper stationOtherMapper;
	
	public String getCreateSql() {
		return Sql.yjy_station;
	}

	public void getNull(HttpServletResponse response) {
		List<StationModel> stationList =new ArrayList<StationModel>();
		excelTemplateExporter.exportExcel(stationList, "���ŵ���ģ��", "����", StationModel.class, "���ŵ���ģ��.xls", response);
		
	}

	public void excleImport(MultipartFile station) {
		//�����ʷ����
		stationOtherMapper.deleteAll();
		//���������,�˹����ӻ�
		List<StationModel> txt = MultipartUtil.getTxt(station,StationModel.class);
		for(StationModel stationModel:txt) {
			stationOtherMapper.insert(stationModel);
		}
		
	}

}
