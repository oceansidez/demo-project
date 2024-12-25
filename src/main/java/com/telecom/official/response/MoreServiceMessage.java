package com.telecom.official.response;

import java.util.List;


/**
 * 多客服消息
 */
public class MoreServiceMessage extends BaseMessage {
	/**
	 * 指定接入的客服
	 */
	private List<Service> TransInfo;

	public List<Service> getTransInfo() {
		return TransInfo;
	}

	public void setTransInfo(List<Service> transInfo) {
		TransInfo = transInfo;
	}
}