package com.yjy.mapper.user_our;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

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
	
	void update(PoliceModel policeModel);
	
	List<PoliceModel> findByCodeAndName(@Param("subcode") String subcode, @Param("policeName") String policeName);
	
	@Update("update T_POLICE_INFO t set t.TELEPHONE=#{subcode,jdbcType=VARCHAR} where t.id=#{id,jdbcType=VARCHAR}")
	void updatePhone(@Param("id") String id,@Param("subcode") String subcode);
	
}
