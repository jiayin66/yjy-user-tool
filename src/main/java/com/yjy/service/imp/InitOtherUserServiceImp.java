package com.yjy.service.imp;

import com.yjy.content.Sql;
import com.yjy.mapper.user_other.UserOtherMapper;
import com.yjy.model.PoliceModel;
import com.yjy.service.InitOtherUserService;
import com.yjy.util.ExcelTemplateExporter;
import com.yjy.util.MultipartUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
@Service
public class InitOtherUserServiceImp implements InitOtherUserService{
	@Autowired
	private UserOtherMapper userOtherMapper;
	@Autowired
	private ExcelTemplateExporter excelTemplateExporter;

	public String getCreateSql() {
		return Sql.yjy_user;
	}

	public void excleImport(MultipartFile police) {
		//�����ʷ����
		userOtherMapper.deleteAll();
		//���������,�˹����ӻ�
		List<PoliceModel> txt = MultipartUtil.getTxt(police,PoliceModel.class);
		for(PoliceModel policeModel:txt) {
			userOtherMapper.insert(policeModel);
		}
	}
	
	
	public void getNull(HttpServletResponse response) {
		List<PoliceModel> policeList =new ArrayList<PoliceModel>();
		excelTemplateExporter.exportExcel(policeList, "ģ��", "��Ա", PoliceModel.class, "ģ��.xls", response);
		
	}
}
