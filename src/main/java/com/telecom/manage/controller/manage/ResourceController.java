package com.telecom.manage.controller.manage;

import com.telecom.base.BaseController;
import com.telecom.bean.Pager;
import com.telecom.bean.RolesBean;
import com.telecom.config.GlobalValue;
import com.telecom.manage.entity.Resource;
import com.telecom.manage.entity.Resource.AllowType;
import com.telecom.manage.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "resource")
public class ResourceController extends BaseController {

    private final String PAGE_PRE = "/manage/resource_";

    @Autowired
    ResourceService resourceService;

    // 列表
    @GetMapping("list")
    public String list(Pager pager, ModelMap model) {
        pager = resourceService.findPager(pager);
        return PAGE_PRE + "list";
    }

    // 添加
    @GetMapping("add")
    public String add(ModelMap model) {
        model.put("isAdd", true);
        return PAGE_PRE + "input";
    }
    
    // 编辑
    @GetMapping("edit/{id}")
    public String edit(@PathVariable(value="id") String id, ModelMap model) {
		Resource resource = resourceService.get(id);
		model.put("resource", resource);
        model.put("isAdd", false);
        return PAGE_PRE + "input";
    }
    
    // 删除
 	@PostMapping("delete")
 	@ResponseBody
 	public String delete(@RequestParam(value="ids") String[] ids) {
 		for (String id : ids) {
 			resourceService.delete(id);
 		}
 		return ajax(Status.success, "删除成功!");
 	}
 	
 	// 保存
 	@PostMapping("save")
 	public String save(Resource resource, ModelMap model) throws Exception {
 		List<String> authorityList = resource.getAuthorityList();
 		resource.setAuthorityList(authorityList);
 		if(AllowType.intercept.toString().equals(resource.getAllowType())){
 			resource.setAuthorityList(resource.getAuthorityList());
 		}
 		else{
 			resource.setAuthorityList(null);
 		}
 		resource = resourceService.save(resource);
 		setSuccess(model, null, "resource/list");
 		return SUCCESS;
 	}

 	// 更新
 	@PostMapping("update")
 	public String update(@RequestParam(value="id") String id, Resource resource, ModelMap model) throws Exception {
 		Resource persistent = resourceService.get(id);
 		List<String> authorityList = resource.getAuthorityList();
 		resource.setAuthorityList(authorityList);
 		persistent.setName(resource.getName());
 		persistent.setPath(resource.getPath());
 		persistent.setAllowType(resource.getAllowType());
 		if(AllowType.intercept.toString().equals(resource.getAllowType())){
 			resource.setAuthorityList(resource.getAuthorityList());
 		}
 		else{
 			resource.setAuthorityList(null);
 		}
 		resourceService.update(persistent);
 		
 		setSuccess(model, null, "resource/list");
 		return SUCCESS;
 	}
 	
 	// 权限刷新
 	@GetMapping("refresh")
 	public String refresh(ModelMap model){
     	return "/manage/resource_refresh";
 	}
 	
 	// 获取权限集合
 	@ModelAttribute
 	public void getAuthRoles(ModelMap model) {
 		List<RolesBean> authRoles = GlobalValue.authRoles;
 		model.addAttribute("authRoles", authRoles);
 	}
}
