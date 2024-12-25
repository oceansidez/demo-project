package com.telecom.manage.mapper;

import com.telecom.base.BaseMapper;
import com.telecom.manage.entity.AuthCode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AuthCodeMapper extends BaseMapper<AuthCode>{

    @Select("select * from t_auth_code where mobile = #{mobile} limit 1")
	public AuthCode getAuthCodeByMobile(String mobile);
}
