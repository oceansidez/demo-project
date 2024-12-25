package com.telecom.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telecom.base.BaseMapper;
import com.telecom.base.BaseServiceImpl;
import com.telecom.entity.BusinessHallStreet;
import com.telecom.mapper.BusinessHallStreetMapper;
import com.telecom.service.BusinessHallStreetService;

@Service
public class BusinessHallStreetServiceImpl extends BaseServiceImpl<BusinessHallStreet> implements BusinessHallStreetService {

    @Autowired
    BusinessHallStreetMapper businessHallStreetMapper;

    @Override
    public BaseMapper<BusinessHallStreet> getBaseMapper() {
        return businessHallStreetMapper;
    }

}

