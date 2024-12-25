package com.telecom.bean;

import java.util.List;

/**
 * 权限集合Bean
 *
 */
public class RolesBean {

	private String name;
	private List<RoleBean> roles;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<RoleBean> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleBean> roles) {
		this.roles = roles;
	}
}
