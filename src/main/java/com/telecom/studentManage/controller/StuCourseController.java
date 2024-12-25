package com.telecom.studentManage.controller;

import com.telecom.base.BaseController;
import com.telecom.bean.Pager;
import com.telecom.manage.service.CourseService;
import com.telecom.studentManage.entity.Course;
import com.telecom.studentManage.entity.StuCourse;
import com.telecom.studentManage.entity.Student;
import com.telecom.studentManage.service.StuCourseService;
import com.telecom.studentManage.service.StudentService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/stu-course")
public class StuCourseController extends BaseController {

    @Autowired
    CourseService courseService;

    @Autowired
    StuCourseService stuCourseService;

    @Autowired
    StudentService studentService;

    @ModelAttribute
    public StuCourse modelAttribute(@PathVariable(value = "id",required = false) String id){
        if (StringUtils.isNotEmpty(id)){
           return stuCourseService.get(id);
        }
        return new StuCourse();
    }

    // 列表
    @GetMapping("index")
    public String list(Pager pager, ModelMap model) {
        pager = stuCourseService.findPager(pager);
        pager.getResult().forEach(item->{
            StuCourse r =  (StuCourse)item;
            r.setStuName(studentService.get(r.getCourseId()).getName());
            r.setCourseName(courseService.get(r.getStuId()).getName());
        });
        model.put("pager", pager);
        return "/studentManage/stu_course_list";
    }

    // 添加
    @GetMapping("add")
    public String add(ModelMap model) {
        model.put("isAdd", true);
        List<Student> studentList = studentService.getAllList();
        List<Course> courseList = courseService.getAllList();
        model.put("studentList", studentList);
        model.put("courseList", courseList);
        return "/studentManage/stu_course_input";
    }

    // 编辑
    @GetMapping("edit/{id}")
    public String edit(@ModelAttribute StuCourse stuCourse, ModelMap model) {
        Student student = studentService.get(stuCourse.getStuId());
        Course course = courseService.get(stuCourse.getCourseId());
        stuCourse.setCourseName(course.getName());
        stuCourse.setStuName(student.getName());
        model.put("stuCourse", stuCourse);
        model.put("isAdd", false);

        List<Student> studentList = studentService.getAllList();
        List<Course> courseList = courseService.getAllList();
        model.put("studentList", studentList);
        model.put("courseList", courseList);
        return "/studentManage/stu_course_input";
    }

    // 删除
    @PostMapping("delete")
    @ResponseBody
    public String delete(@RequestParam(value = "ids") String[] ids) {
        try {
            // 遍历删除
            for (String id : ids) {
                stuCourseService.delete(id);
            }
            return ajax(Status.success, "删除成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 保存
    @PostMapping("save")
    public String save(StuCourse stuCourse, ModelMap model) throws Exception {
        stuCourseService.save(stuCourse);
        setSuccess(model, null, "stu-course/index");
        return SUCCESS;
    }

    // 更新
    @PostMapping("update")
    public String update(StuCourse stuCourse, ModelMap model) throws Exception {
        stuCourseService.update(stuCourse);
        setSuccess(model, null, "stu-course/index");
        return SUCCESS;
    }

}
