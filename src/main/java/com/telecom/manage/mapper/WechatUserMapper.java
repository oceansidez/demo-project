package com.telecom.manage.mapper;

import com.telecom.base.BaseMapper;
import com.telecom.manage.entity.WechatUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface WechatUserMapper extends BaseMapper<WechatUser> {

	@Select("select * from t_wechat_user where open_id = #{openId} limit 1")
	WechatUser findByOpenId(String openId);
	
}
