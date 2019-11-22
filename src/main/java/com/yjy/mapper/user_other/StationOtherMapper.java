package com.yjy.mapper.user_other;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.yjy.model.StationModel;

public interface StationOtherMapper {
	
	List<StationModel> findAllStation();

	void insert(StationModel StationModel);

	@Delete("delete from YJY_STATION")
	void deleteAll();

	@Delete("delete from YJY_STATION where id=#{id,jdbcType=VARCHAR}")
	void deleteById(String id);

	void update(StationModel stationModel);

	@Update("update YJY_STATION  set STATIONNUM=#{stationnum,jdbcType=VARCHAR},LONGITUDE=#{longitude,jdbcType=VARCHAR},LATITUDE=#{latitude,jdbcType=VARCHAR} where orgcode=#{orgcode,jdbcType=VARCHAR}")
	void updateXY(@Param("stationnum") String stationnum,@Param("longitude")String longitude,@Param("latitude") String latitude,@Param("orgcode") String orgcode);
}
