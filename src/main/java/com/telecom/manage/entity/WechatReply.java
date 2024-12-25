package com.telecom.manage.entity;

import com.telecom.base.BaseEntity;

import javax.persistence.Table;

/**
 * 微信回复
 *
 */
@Table(name = "t_wechat_reply")
public class WechatReply extends BaseEntity {

	private static final long serialVersionUID = -2122256781885357340L;

	public static final String CONTENT_TYPE_GRAPHICS = "graphics";
	public static final String CONTENT_TYPE_TEXT = "text";
	public static final String CONTENT_TYPE_IMAGE = "image";
	
	// 回复类型（关键字回复，关注回复，默认回复， 菜单事件回复）
	public enum ReplyType {
		keyword, attention, defaults, click
	}

	// 回复内容类型（图文，文本，图片）
	public enum ContentType {
		graphics, text, image
	}
	
	private String type; // 类型
	private String title; // 标题
	private String content; // 内容
	private String keyword; // 关键字
	private String imgUrl; // 图片
	private String url; // 跳转链接
	private Boolean isOpen; // 是否开启
	private String contentType; // 回复消息类型

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Boolean isOpen) {
		this.isOpen = isOpen;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
