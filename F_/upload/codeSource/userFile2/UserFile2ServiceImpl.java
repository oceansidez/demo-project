package com.telecom.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telecom.base.BaseMapper;
import com.telecom.base.BaseServiceImpl;
import com.telecom.entity.UserFile2;
import com.telecom.mapper.UserFile2Mapper;
import com.telecom.service.UserFile2Service;

@Service
public class UserFile2ServiceImpl extends BaseServiceImpl<UserFile2> implements UserFile2Service {

    @Autowired
    UserFile2Mapper userFile2Mapper;

    @Override
    public BaseMapper<UserFile2> getBaseMapper() {
        return userFile2Mapper;
    }

}

