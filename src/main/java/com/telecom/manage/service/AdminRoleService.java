package com.telecom.manage.service;

import com.telecom.base.BaseService;
import com.telecom.manage.entity.Admin;
import com.telecom.manage.entity.AdminRole;
import com.telecom.manage.entity.Role;

import java.util.List;

public interface AdminRoleService extends BaseService<AdminRole>{

	/**
	 * 获取指定用户的角色列表
	 * @param adminId
	 * @return
	 */
	public List<Role> getRoleList(String adminId);
	
	/**
	 * 获取拥有指定角色的用户列表
	 * @param roleId
	 * @return
	 */
	public List<Admin> getAdminList(String roleId);
	
	/**
	 * 删除指定用户的关联表数据
	 * @param adminId
	 * @return
	 */
	public Integer deleteAdminRoleByAdmin(String adminId);
	
	/**
	 * 新增用户的关联表数据
	 * @param adminId
	 * @param roleIds
	 * @return
	 */
	public Integer batchInsertAdminRole(String adminId, List<String> roleIds);
}
