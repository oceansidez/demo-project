package com.telecom.studentManage.controller;

import com.telecom.base.BaseController;
import com.telecom.bean.Pager;
import com.telecom.manage.service.CourseService;
import com.telecom.studentManage.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "course")
public class CourseController extends BaseController {

	@Autowired
	CourseService courseService;

	// 列表
	@GetMapping("index")
	public String list(Pager pager, ModelMap model) {
		pager = courseService.findPager(pager);
		model.put("pager", pager);
		return "/studentManage/course_list";
	}
	
	// 添加
	@GetMapping("add")
	public String add(ModelMap model) {
		model.put("isAdd", true);
		return "/studentManage/course_input";
	}

	// 编辑
	@GetMapping("edit/{id}")
	public String edit(@PathVariable(value="id") String id, ModelMap model) {
		Course course = courseService.get(id);
		model.put("course", course);
		model.put("isAdd", false);
		return "/studentManage/course_input";
	}

	// 删除
	@PostMapping("delete")
	@ResponseBody
	public String delete(@RequestParam(value="ids") String[] ids) {
		try {
			// 遍历删除
			for (String id : ids) {
				courseService.delete(id);
			}
			return ajax(Status.success, "删除成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
//
	// 保存
	@PostMapping("save")
	public String save(Course course, ModelMap model) throws Exception {
		courseService.save(course);
		setSuccess(model, null, "course/index");
		return SUCCESS;
	}

	// 更新
	@PostMapping("update")
	public String update(Course course, ModelMap model) throws Exception {
		courseService.update(course);
		setSuccess(model, null, "course/index");
		return SUCCESS;
	}
}
