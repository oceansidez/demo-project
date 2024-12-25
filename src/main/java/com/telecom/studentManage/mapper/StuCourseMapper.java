package com.telecom.studentManage.mapper;

import com.telecom.base.BaseMapper;
import com.telecom.studentManage.entity.StuCourse;
import com.telecom.studentManage.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StuCourseMapper extends BaseMapper<StuCourse> {

	@Select("select course.* from t_stu_course as stuCourse "
			+ " left join t_course as course "
			+ " on stuCourse.course_id = course.id "
			+ " where stuCourse.stu_id = #{id} ")
    public List<Student> getStudentList(String stuId);
	
	@Select("select stu.* from t_stu_course as stuCourse "
			+ " left join t_stu as stu "
			+ " on stuCourse.stu_id = stu.id "
			+ " where stuCourse.course_id = #{id} ")
    public List<StuCourse> getCourseList(String courseId);

	@Delete("delete from t_stu_course where stu_id = #{id}")
	public Integer deleteStuCourseByStu(String stuId);
	
	@Insert( "<script>"+
             "insert into t_stu_course(stu_id, course_id) "
           + "values "
           + "<foreach collection =\"courseIds\" item=\"course\" index= \"index\" separator =\",\"> "
           + "(#{stuId}, #{course}) "
           + "</foreach > "
           + "</script>" )
	public Integer batchInsertStuCourse(@Param("stuId") String stuId, @Param("courseIds") List<String> courseIds);

}
