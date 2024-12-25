package com.telecom.manage.mapper;

import com.telecom.base.BaseMapper;
import com.telecom.manage.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminMapper extends BaseMapper<Admin> {

	@Select("select * from t_admin where username = #{username}")
	public Admin getByUsername(String username);
	
	@Select("select * from t_admin where mobile = #{mobile}")
	public Admin getByMobile(String mobile);
}
