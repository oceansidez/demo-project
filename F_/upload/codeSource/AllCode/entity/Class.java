package com.telecom.entity;

import java.util.Date;
import java.math.BigDecimal;
import javax.persistence.Table;

import com.telecom.base.BaseEntity;

@Table(name = "t_class")
public class Class extends BaseEntity {

    private static final long serialVersionUID = -1L;

    private Integer name;// 

    public Integer getName() {
        return name;
    }

    public void setName(Integer name) {
        this.name = name;
    }


}
