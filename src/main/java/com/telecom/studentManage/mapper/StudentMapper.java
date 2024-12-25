package com.telecom.studentManage.mapper;

import com.telecom.base.BaseMapper;
import com.telecom.studentManage.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {

	@Select("select * from t_stu where stu_name = #{stuname}")
	public Student getByUsername(String stuName);
	
	@Select("select * from t_stu where stu_sorc = #{sorc}")
	public Student getByMobile(String stuSorc);
}
