package com.telecom.manage.service.impl;

import com.telecom.base.BaseMapper;
import com.telecom.base.BaseServiceImpl;
import com.telecom.studentManage.entity.Course;
import com.telecom.manage.mapper.CourseMapper;
import com.telecom.manage.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl extends BaseServiceImpl<Course> implements CourseService {

    @Autowired
    CourseMapper courseMapper;
    
    @Override
    public BaseMapper<Course> getBaseMapper() {
    	return courseMapper;
    }

}
