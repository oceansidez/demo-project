package com.telecom.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telecom.base.BaseMapper;
import com.telecom.base.BaseServiceImpl;
import com.telecom.entity.BusinessHall;
import com.telecom.mapper.BusinessHallMapper;
import com.telecom.service.BusinessHallService;

@Service
public class BusinessHallServiceImpl extends BaseServiceImpl<BusinessHall> implements BusinessHallService {

    @Autowired
    BusinessHallMapper businessHallMapper;

    @Override
    public BaseMapper<BusinessHall> getBaseMapper() {
        return businessHallMapper;
    }

}

