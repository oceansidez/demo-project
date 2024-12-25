package com.telecom.studentManage.entity;

import com.telecom.base.BaseEntity;
import lombok.Data;

import javax.persistence.Table;

@Table(name = "t_course")
@Data
public class Course extends BaseEntity {
    private String name;
    private String remark;
}
