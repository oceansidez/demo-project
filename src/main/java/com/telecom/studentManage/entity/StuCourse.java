package com.telecom.studentManage.entity;

import com.telecom.base.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "t_stu_course")
@Data
public class StuCourse extends BaseEntity {

    private String stuId;

    @Transient
    private String stuName;

    private String courseId;

    @Transient
    private String courseName;

    private String score;

    private String remark;
}
