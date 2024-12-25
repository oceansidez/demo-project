package com.telecom.manage.mapper;

import com.telecom.base.BaseMapper;
import com.telecom.manage.entity.Score;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ScoreMapper extends BaseMapper<Score> {

	@Select("select * from t_score where id = #{id}")
	@Results({
	       @Result(property = "admin",
	               column = "admin_id",
	               one = @One(select = "com.telecom.mapper.AdminMapper.selectForDefault"))
	})
	public Score getWithFKData(String id);
}
