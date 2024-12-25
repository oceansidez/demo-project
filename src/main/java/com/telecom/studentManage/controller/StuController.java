package com.telecom.studentManage.controller;

import com.telecom.base.BaseController;
import com.telecom.bean.Pager;
import com.telecom.manage.service.CourseService;
import com.telecom.studentManage.entity.Student;
import com.telecom.studentManage.service.StuCourseService;
import com.telecom.studentManage.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "student")
public class StuController extends BaseController {

    @Autowired
    StudentService studentService;

    @Autowired
    StuCourseService stuCourseService;

    @Autowired
    CourseService courseService;

    @RequestMapping("index")
    public String index(Pager pager, ModelMap model) {
        pager = studentService.findPager(pager);
        model.put("pager", pager);
        return "/studentManage/stu_list";
    }

    @GetMapping("add")
    public String add(ModelMap model) {
        model.put("isAdd", true);
        return "/studentManage/stu_input";
    }

    // 保存
    @PostMapping("save")
    @Transactional
    public String save(Student student, ModelMap model) {
        // 保存用户信息
        studentService.save(student);
        setSuccess(model, null, "student/index");
        return SUCCESS;
    }

    // 删除
    @PostMapping("delete")
    @ResponseBody
    public String delete(@RequestParam(value = "ids") String[] ids) {
        for (String id : ids) {
            studentService.delete(id);
        }
        return ajax(Status.success, "删除成功!");
    }


    // 更新
    @PostMapping("update")
    public String update(Student student, ModelMap model) {
        studentService.update(student);
        setSuccess(model, null, "student/index");
        return SUCCESS;
    }

    // 编辑
    @GetMapping("edit/{id}")
    public String edit(@PathVariable(value = "id") String id, ModelMap model) {
        Student student = studentService.get(id);
        model.put("isAdd", false);
        model.put("student", student);
        return "/studentManage/stu_input";
    }

}
