package com.telecom.manage.entity;

import com.telecom.base.BaseEntity;
import com.telecom.util.JsonUtil;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Table(name = "t_role")
public class Role extends BaseEntity {

	private static final long serialVersionUID = 1604556530491930723L;

	public static final String ROLE_BASE = "ROLE_BASE";// 基础管理权限
	
	private String name;
	private String description;
	private String authorityListStore;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthorityListStore() {
		return authorityListStore;
	}

	public void setAuthorityListStore(String authorityListStore) {
		this.authorityListStore = authorityListStore;
	}
	
	// 获取权限集合
	@SuppressWarnings("unchecked")
	public List<String> getAuthorityList() {
		if (StringUtils.isEmpty(authorityListStore)) {
			return null;
		}
		return JsonUtil.toObject(authorityListStore, ArrayList.class);
	}
		
	// 设置权限集合
	public void setAuthorityList(List<String> roleList) {
		if (roleList == null || roleList.size() == 0) {
			authorityListStore = null;
			return;
		}
		authorityListStore = JsonUtil.toJson(roleList);
	}

}
