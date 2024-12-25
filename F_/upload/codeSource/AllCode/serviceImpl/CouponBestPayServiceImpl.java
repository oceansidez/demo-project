package com.telecom.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telecom.base.BaseMapper;
import com.telecom.base.BaseServiceImpl;
import com.telecom.entity.CouponBestPay;
import com.telecom.mapper.CouponBestPayMapper;
import com.telecom.service.CouponBestPayService;

@Service
public class CouponBestPayServiceImpl extends BaseServiceImpl<CouponBestPay> implements CouponBestPayService {

    @Autowired
    CouponBestPayMapper couponBestPayMapper;

    @Override
    public BaseMapper<CouponBestPay> getBaseMapper() {
        return couponBestPayMapper;
    }

}

