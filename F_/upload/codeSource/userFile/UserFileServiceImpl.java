package com.telecom.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telecom.base.BaseMapper;
import com.telecom.base.BaseServiceImpl;
import com.telecom.entity.UserFile;
import com.telecom.mapper.UserFileMapper;
import com.telecom.service.UserFileService;

@Service
public class UserFileServiceImpl extends BaseServiceImpl<UserFile> implements UserFileService {

    @Autowired
    UserFileMapper userFileMapper;

    @Override
    public BaseMapper<UserFile> getBaseMapper() {
        return userFileMapper;
    }

}

