package com.telecom.official.request;

/**
 * 图片消息
 */
public class ImageMessage extends BaseMessage {
	// 图片链接
	private String MediaId;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
}
