package com.telecom.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telecom.base.BaseMapper;
import com.telecom.base.BaseServiceImpl;
import com.telecom.entity.Admin;
import com.telecom.mapper.AdminMapper;
import com.telecom.service.AdminService;

@Service
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService {

    @Autowired
    AdminMapper adminMapper;

    @Override
    public BaseMapper<Admin> getBaseMapper() {
        return adminMapper;
    }

}

