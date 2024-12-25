package com.telecom.manage.service.impl;

import com.telecom.base.BaseMapper;
import com.telecom.base.BaseServiceImpl;
import com.telecom.manage.entity.Admin;
import com.telecom.manage.entity.AdminRole;
import com.telecom.manage.entity.Role;
import com.telecom.manage.mapper.AdminRoleMapper;
import com.telecom.manage.service.AdminRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminRoleServiceImpl extends BaseServiceImpl<AdminRole> implements AdminRoleService {
	
    @Autowired
    AdminRoleMapper adminRoleMapper;
    
    @Override
    public BaseMapper<AdminRole> getBaseMapper() {
    	return adminRoleMapper;
    }

	public List<Role> getRoleList(String adminId) {
    	return adminRoleMapper.getRoleList(adminId);
    }
	
	public List<Admin> getAdminList(String roleId) {
    	return adminRoleMapper.getAdminList(roleId);
    }
	
	public Integer deleteAdminRoleByAdmin(String adminId) {
    	return adminRoleMapper.deleteAdminRoleByAdmin(adminId);
    }
	
	public Integer batchInsertAdminRole(String adminId, List<String> roleIds) {
    	return adminRoleMapper.batchInsertAdminRole(adminId, roleIds);
    }

}
