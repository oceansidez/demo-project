package com.telecom.entity;

import java.util.Date;
import java.math.BigDecimal;
import javax.persistence.Table;

import com.telecom.base.BaseEntity;

@Table(name = "t_coupon_best_pay")
public class CouponBestPay extends BaseEntity {

    private static final long serialVersionUID = -1L;

    private Boolean isDelete;// 删除 1.yes 0.no
    private String couponId;// 卡券主键id
    private String bestPayNo;// 翼支付卡券的支付券号

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

    public String getBestPayNo() {
        return bestPayNo;
    }

    public void setBestPayNo(String bestPayNo) {
        this.bestPayNo = bestPayNo;
    }


}
