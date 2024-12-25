package com.telecom.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telecom.base.BaseMapper;
import com.telecom.base.BaseServiceImpl;
import com.telecom.entity.AuthCode;
import com.telecom.mapper.AuthCodeMapper;
import com.telecom.service.AuthCodeService;

@Service
public class AuthCodeServiceImpl extends BaseServiceImpl<AuthCode> implements AuthCodeService {

    @Autowired
    AuthCodeMapper authCodeMapper;

    @Override
    public BaseMapper<AuthCode> getBaseMapper() {
        return authCodeMapper;
    }

}

