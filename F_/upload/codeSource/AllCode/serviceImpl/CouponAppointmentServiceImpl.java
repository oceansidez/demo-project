package com.telecom.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telecom.base.BaseMapper;
import com.telecom.base.BaseServiceImpl;
import com.telecom.entity.CouponAppointment;
import com.telecom.mapper.CouponAppointmentMapper;
import com.telecom.service.CouponAppointmentService;

@Service
public class CouponAppointmentServiceImpl extends BaseServiceImpl<CouponAppointment> implements CouponAppointmentService {

    @Autowired
    CouponAppointmentMapper couponAppointmentMapper;

    @Override
    public BaseMapper<CouponAppointment> getBaseMapper() {
        return couponAppointmentMapper;
    }

}

