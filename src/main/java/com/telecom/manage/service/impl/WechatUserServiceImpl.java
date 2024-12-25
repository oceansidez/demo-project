package com.telecom.manage.service.impl;

import com.telecom.base.BaseMapper;
import com.telecom.base.BaseServiceImpl;
import com.telecom.config.OfficialConfig;
import com.telecom.manage.entity.WechatUser;
import com.telecom.manage.mapper.WechatUserMapper;
import com.telecom.manage.service.WechatUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WechatUserServiceImpl extends BaseServiceImpl<WechatUser> implements WechatUserService {

    @Autowired
    WechatUserMapper wechatUserMapper;
    
    @Autowired
    OfficialConfig officialConfig;
    
    @Override
    public BaseMapper<WechatUser> getBaseMapper() {
    	return wechatUserMapper;
    }

    public WechatUser findByOpenId(String openId) {
    	return wechatUserMapper.findByOpenId(openId);
    }
}
