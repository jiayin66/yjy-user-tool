package com.yjy.mapper.user_our;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

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
	
	void update(PoliceModel policeModel);
	
	List<PoliceModel> findByCodeAndName(@Param("subcode") String subcode, @Param("policeName") String policeName);
	
	@Update("update T_POLICE_INFO t set t.TELEPHONE=#{subcode,jdbcType=VARCHAR} where t.id=#{id,jdbcType=VARCHAR}")
	void updatePhone(@Param("id") String id,@Param("subcode") String subcode);
	
}
