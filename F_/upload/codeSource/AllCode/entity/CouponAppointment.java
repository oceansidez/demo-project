package com.telecom.entity;

import java.util.Date;
import java.math.BigDecimal;
import javax.persistence.Table;

import com.telecom.base.BaseEntity;

@Table(name = "t_coupon_appointment")
public class CouponAppointment extends BaseEntity {

    private static final long serialVersionUID = -1L;

    private Boolean isDelete;// 删除 1.yes 0.no
    private String couponId;// 卡券主键
    private Integer week;// 星期
    private Date beginTime;// 开始时间
    private Date endTime;// 结束时间
    private Integer totalQuantity;// 总名额
    private String preType;// 预约策略时间选择方式
    private Date selectDay;// 按天多选
    private Date startDate;// 按时间段，开始时间
    private Date endDate;// 按时间段，结束时间

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getPreType() {
        return preType;
    }

    public void setPreType(String preType) {
        this.preType = preType;
    }

    public Date getSelectDay() {
        return selectDay;
    }

    public void setSelectDay(Date selectDay) {
        this.selectDay = selectDay;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


}
