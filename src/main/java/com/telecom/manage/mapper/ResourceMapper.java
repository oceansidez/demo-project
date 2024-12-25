package com.telecom.manage.mapper;

import com.telecom.base.BaseMapper;
import com.telecom.manage.entity.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ResourceMapper extends BaseMapper<Resource> {

	@Select("select * from t_resource where allow_type = #{allowType}")
	List<Resource> selectAllowResource(String allowType);
}

