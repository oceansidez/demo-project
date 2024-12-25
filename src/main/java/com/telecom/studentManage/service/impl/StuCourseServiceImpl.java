package com.telecom.studentManage.service.impl;

import com.telecom.base.BaseMapper;
import com.telecom.base.BaseServiceImpl;
import com.telecom.studentManage.entity.StuCourse;
import com.telecom.studentManage.entity.Student;
import com.telecom.studentManage.mapper.StuCourseMapper;
import com.telecom.studentManage.service.StuCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StuCourseServiceImpl extends BaseServiceImpl<StuCourse> implements StuCourseService {
	
    @Autowired
    StuCourseMapper stuCourseMapper;


    @Override
    public List<StuCourse> getCourseList(String stuId) {
        return stuCourseMapper.getCourseList(stuId);
    }

    @Override
    public List<Student> getStudentList(String courseId) {
        return stuCourseMapper.getStudentList(courseId);
    }

    @Override
    public Integer deleteStuCourseByStu(String stuId) {
        return stuCourseMapper.deleteStuCourseByStu(stuId);
    }

    @Override
    @Transactional
    public Integer batchInsertStuCourse(String stuId, List<String> courseIds) {
        return stuCourseMapper.batchInsertStuCourse(stuId,courseIds);
    }

    @Override
    public BaseMapper<StuCourse> getBaseMapper() {
        return stuCourseMapper;
    }
}
