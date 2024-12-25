package com.telecom.entity;

import java.util.Date;
import java.math.BigDecimal;
import javax.persistence.Table;

import com.telecom.base.BaseEntity;

@Table(name = "t_user_file2")
public class UserFile2 extends BaseEntity {

    private static final long serialVersionUID = -1L;

    private String name;// am

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
