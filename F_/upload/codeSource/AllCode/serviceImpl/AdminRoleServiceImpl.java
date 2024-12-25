package com.telecom.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telecom.base.BaseMapper;
import com.telecom.base.BaseServiceImpl;
import com.telecom.entity.AdminRole;
import com.telecom.mapper.AdminRoleMapper;
import com.telecom.service.AdminRoleService;

@Service
public class AdminRoleServiceImpl extends BaseServiceImpl<AdminRole> implements AdminRoleService {

    @Autowired
    AdminRoleMapper adminRoleMapper;

    @Override
    public BaseMapper<AdminRole> getBaseMapper() {
        return adminRoleMapper;
    }

}

