package com.telecom.studentManage.entity;

import com.telecom.base.BaseEntity;
import lombok.Data;

import javax.persistence.Table;


@Table(name = "t_student")
@Data
public class Student extends BaseEntity {
    private String name;
    private String age;
    private String remark;
}
