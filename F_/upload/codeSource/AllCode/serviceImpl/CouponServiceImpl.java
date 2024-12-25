package com.telecom.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telecom.base.BaseMapper;
import com.telecom.base.BaseServiceImpl;
import com.telecom.entity.Coupon;
import com.telecom.mapper.CouponMapper;
import com.telecom.service.CouponService;

@Service
public class CouponServiceImpl extends BaseServiceImpl<Coupon> implements CouponService {

    @Autowired
    CouponMapper couponMapper;

    @Override
    public BaseMapper<Coupon> getBaseMapper() {
        return couponMapper;
    }

}

