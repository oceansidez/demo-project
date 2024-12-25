package com.telecom.entity;

import java.util.Date;
import java.math.BigDecimal;
import javax.persistence.Table;

import com.telecom.base.BaseEntity;

@Table(name = "t_user_file")
public class UserFile extends BaseEntity {

    private static final long serialVersionUID = -1L;

    private String uName;// name

    public String getUName() {
        return uName;
    }

    public void setUName(String uName) {
        this.uName = uName;
    }


}
