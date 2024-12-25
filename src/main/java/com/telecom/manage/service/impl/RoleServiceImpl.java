package com.telecom.manage.service.impl;

import com.telecom.base.BaseMapper;
import com.telecom.base.BaseServiceImpl;
import com.telecom.manage.entity.Role;
import com.telecom.manage.mapper.RoleMapper;
import com.telecom.manage.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

    @Autowired
    RoleMapper roleMapper;
    
    @Override
    public BaseMapper<Role> getBaseMapper() {
    	return roleMapper;
    }

}
