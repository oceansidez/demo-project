package com.telecom.manage.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "t_admin_role")
public class AdminRole implements Serializable {

	private static final long serialVersionUID = -1934863290286909949L;

	@Id
	private String adminId;
	@Id
	private String roleId;

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}
