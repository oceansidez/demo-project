package com.telecom.manage.service.impl;

import com.telecom.base.BaseMapper;
import com.telecom.base.BaseServiceImpl;
import com.telecom.manage.entity.WechatReply;
import com.telecom.manage.mapper.WechatReplyMapper;
import com.telecom.manage.service.WechatReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class WechatReplyServiceImpl extends BaseServiceImpl<WechatReply> implements WechatReplyService {

    @Autowired
    WechatReplyMapper wechatReplyMapper;
    
    @Override
    public BaseMapper<WechatReply> getBaseMapper() {
    	return wechatReplyMapper;
    }

    public List<WechatReply> getListByConditions(Map<String, Object> map) {
    	return wechatReplyMapper.getListByConditions(map);
    }
    
    public int getCountByConditions(Map<String, Object> map) {
    	return wechatReplyMapper.getCountByConditions(map);
    }
    
}
