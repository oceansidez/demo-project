package com.telecom.manage.service;

import com.telecom.base.BaseService;
import com.telecom.manage.entity.WechatUser;

public interface WechatUserService extends BaseService<WechatUser>{

	/**
	 * 根据openId查询对象
	 * @param openId
	 * @return
	 */
	public WechatUser findByOpenId(String openId);

}
