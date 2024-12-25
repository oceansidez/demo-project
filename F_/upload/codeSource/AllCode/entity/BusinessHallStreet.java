package com.telecom.entity;

import java.util.Date;
import java.math.BigDecimal;
import javax.persistence.Table;

import com.telecom.base.BaseEntity;

@Table(name = "t_business_hall_street")
public class BusinessHallStreet extends BaseEntity {

    private static final long serialVersionUID = -1L;

    private String businessHallId;// 厅店id
    private String streetId;// 街道id

    public String getBusinessHallId() {
        return businessHallId;
    }

    public void setBusinessHallId(String businessHallId) {
        this.businessHallId = businessHallId;
    }

    public String getStreetId() {
        return streetId;
    }

    public void setStreetId(String streetId) {
        this.streetId = streetId;
    }


}
