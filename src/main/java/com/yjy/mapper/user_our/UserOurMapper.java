package com.yjy.mapper.user_our;

import java.util.List;

import com.yjy.model.PoliceModel;
import com.yjy.model.StationModel;

public interface UserOurMapper {
	/**
	 * 查我们的部门为了比对是否存在，最重要的是orgcode,其他不关心
	 * @return
	 */

	List<StationModel> findAllStation();
	/**
	 * 查我们的警员为了比对是否存在，最重要的是policeCode,其他不关心
	 * @return
	 */
	List<PoliceModel> findAllPolice();
	
}
