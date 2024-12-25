package com.telecom.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telecom.base.BaseMapper;
import com.telecom.base.BaseServiceImpl;
import com.telecom.entity.Class;
import com.telecom.mapper.ClassMapper;
import com.telecom.service.ClassService;

@Service
public class ClassServiceImpl extends BaseServiceImpl<Class> implements ClassService {

    @Autowired
    ClassMapper classMapper;

    @Override
    public BaseMapper<Class> getBaseMapper() {
        return classMapper;
    }

}

