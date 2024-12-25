package com.telecom.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telecom.base.BaseMapper;
import com.telecom.base.BaseServiceImpl;
import com.telecom.entity.CodeSource;
import com.telecom.mapper.CodeSourceMapper;
import com.telecom.service.CodeSourceService;

@Service
public class CodeSourceServiceImpl extends BaseServiceImpl<CodeSource> implements CodeSourceService {

    @Autowired
    CodeSourceMapper codeSourceMapper;

    @Override
    public BaseMapper<CodeSource> getBaseMapper() {
        return codeSourceMapper;
    }

}

