package com.telecom.studentManage.service.impl;

import com.telecom.base.BaseMapper;
import com.telecom.base.BaseServiceImpl;
import com.telecom.studentManage.entity.Student;
import com.telecom.studentManage.mapper.StudentMapper;
import com.telecom.studentManage.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl extends BaseServiceImpl<Student> implements StudentService {

    @Autowired
    StudentMapper studentMapper;

    @Override
    public BaseMapper<Student> getBaseMapper() {
        return studentMapper;
    }

    public List<String> getStudentIdList() {
        List<String> list = new ArrayList<String>();
        List<Student> studentList = studentMapper.selectAll();
        if(studentList != null && studentList.size() > 0){
            for(Student student : studentList) {
                list.add(student.getId());
            }
            return list;
        }
        else return null;
    }

}