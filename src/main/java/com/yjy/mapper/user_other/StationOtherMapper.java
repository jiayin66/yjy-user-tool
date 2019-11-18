package com.yjy.mapper.user_other;

import java.util.List;

import org.apache.ibatis.annotations.Delete;

import com.yjy.model.StationModel;

public interface StationOtherMapper {
	
	List<StationModel> findAllStation();

	void insert(StationModel StationModel);

	@Delete("delete from YJY_STATION")
	void deleteAll();

	@Delete("delete from YJY_STATION where id=#{id,jdbcType=VARCHAR}")
	void deleteById(String id);

	void update(StationModel stationModel);

}
