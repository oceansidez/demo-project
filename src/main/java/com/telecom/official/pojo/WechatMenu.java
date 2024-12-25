package com.telecom.official.pojo;

import java.util.List;

public class WechatMenu {
	private String name;

	private String type;

	private String url;

	private String key;

	private List<WechatMenu> items;

	// 前端展示字段
	private String value; // key和url通用字段
	private Integer index; // 定位菜单位置

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<WechatMenu> getItems() {
		return items;
	}

	public void setItems(List<WechatMenu> items) {
		this.items = items;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

}
