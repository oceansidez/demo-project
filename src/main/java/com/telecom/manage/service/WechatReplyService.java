package com.telecom.manage.service;

import com.telecom.base.BaseService;
import com.telecom.manage.entity.WechatReply;

import java.util.List;
import java.util.Map;

public interface WechatReplyService extends BaseService<WechatReply> {

	/**
	 * 条件查询集合
	 * @param map
	 * @return
	 */
	public List<WechatReply> getListByConditions(Map<String, Object> map);
	
	/**
	 * 条件查询数量
	 * @param map
	 * @return
	 */
	public int getCountByConditions(Map<String, Object> map);
	
}
