package com.telecom.controller.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.telecom.base.BaseController;
import com.telecom.bean.Pager;
import com.telecom.entity.UserFile2;
import com.telecom.service.UserFile2Service;

@Controller
@RequestMapping(value = "userFile2")
public class UserFile2Controller extends BaseController {

    private final String PAGE_PRE = "/manage/userFile2_";

    @Autowired
    UserFile2Service userFile2Service;

    // 列表
    @GetMapping("list")
    public String list(Pager pager, ModelMap model) {
        pager = userFile2Service.findPager(pager);
        return PAGE_PRE + "list";
    }

    // 查看
    @GetMapping("view/{id}")
    public String view(@PathVariable(value = "id") String id, ModelMap model) {
        UserFile2 userFile2 = userFile2Service.get(id);
        model.put("userFile2", userFile2);
        return PAGE_PRE + "view";
    }

    // 添加
    @GetMapping("add")
    public String add(ModelMap model) {
        model.put("isAdd", true);
        return PAGE_PRE + "input";
    }

    // 编辑
    @GetMapping("edit/{id}")
    public String edit(@PathVariable(value = "id") String id, ModelMap model) {
        UserFile2 userFile2 = userFile2Service.get(id);
        model.put("userFile2", userFile2);
        model.put("isAdd", false);
        return PAGE_PRE + "input";
    }
}
