package com.telecom.manage.service;

import com.telecom.official.pojo.AccessToken;

import java.util.Map;

public interface WechatService {

	/**
	 * 获取/刷新AccessToken
	 * @return
	 */
	public AccessToken getAccessToken();


	/**
	 * 内容型推送处理
	 * @param requestMap
	 * @param fromUserName
	 * @param toUserName
	 * @param msgType
	 * @return
	 */
	public String contentOperate(Map<String, String> requestMap,
			String fromUserName, String toUserName, String msgType);
	
	/**
	 * 事件型推送处理
	 * @param eventType
	 * @param eventKey
	 * @param fromUserName
	 * @param toUserName
	 * @return
	 */
	public String eventOperate(String eventType, String eventKey,
			String fromUserName, String toUserName);
}
