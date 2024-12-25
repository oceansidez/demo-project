package com.telecom.studentManage.service;

import com.telecom.base.BaseService;
import com.telecom.studentManage.entity.Course;
import com.telecom.studentManage.entity.StuCourse;
import com.telecom.studentManage.entity.Student;

import java.util.List;

public interface StuCourseService extends BaseService<StuCourse>{

	/**
	 * 获取指定用户的角色列表
	 */
	public List<StuCourse> getCourseList(String stuId);
	
	/**
	 * 获取拥有指定角色的用户列表
	 */
	public List<Student> getStudentList(String courseId);
	
	/**
	 * 删除指定用户的关联表数据
	 */
	public Integer deleteStuCourseByStu(String stuId);
	
	/**
	 * 新增用户的关联表数据
	 */
	public Integer batchInsertStuCourse(String stuId, List<String> courseIds);
}
