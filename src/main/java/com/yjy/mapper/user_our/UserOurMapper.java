package com.yjy.mapper.user_our;

import java.util.List;

import com.yjy.model.PoliceModel;
import com.yjy.model.StationModel;

public interface UserOurMapper {
	/**
	 * �����ǵĲ���Ϊ�˱ȶ��Ƿ���ڣ�����Ҫ����orgcode,����������
	 * @return
	 */

	List<StationModel> findAllStation();
	/**
	 * �����ǵľ�ԱΪ�˱ȶ��Ƿ���ڣ�����Ҫ����policeCode,����������
	 * @return
	 */
	List<PoliceModel> findAllPolice();
	
}
