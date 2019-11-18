package com.yjy.mapper.user_other;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.yjy.model.PoliceModel;
import com.yjy.model.StationModel;

public interface UserOtherMapper {
	
	List<PoliceModel> findAllPolice();
	
	void insert(PoliceModel policeModel);
	
	@Delete("delete from YJY_USER")
	void deleteAll();

	@Delete("delete from YJY_USER where id=#{id,jdbcType=VARCHAR}")
	void deleteById(String id);

	@Update("update YJY_USER t set t.SEX=#{value,jdbcType=VARCHAR} where t.SEX=#{key,jdbcType=VARCHAR}")
	void updateSex(@Param("key") String key,@Param("value") String value);

	List<String> findForStationId(@Param("type") Integer type);

	void updateStationId(String s, String ourId,Integer type);

}
