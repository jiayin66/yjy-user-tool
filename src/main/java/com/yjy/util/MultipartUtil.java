package com.yjy.util;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;

public class MultipartUtil {
	/**
	 * ͨ�õĶ�ȡexcle����
	 * @param file
	 * @param cla
	 * @return
	 */
	public static  <T>  List<T> getTxt(MultipartFile file,Class cla){
		try {
			ImportParams params = new ImportParams();
			 params.setTitleRows(0);
			params.setNeedVerfiy(false);
			ExcelImportResult<T> resul = ExcelImportUtil.importExcelMore(file.getInputStream(), cla,
					params);
			List<T> list = resul.getList();
			return list;
		} catch (Exception e) {
			System.out.println("����ԭ��"+e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("��ȡ:"+cla.getSimpleName()+"excel����ʧ��");
			
		}
		//return null;
	}

}
