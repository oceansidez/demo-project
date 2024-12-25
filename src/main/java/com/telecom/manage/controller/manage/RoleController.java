package com.telecom.manage.controller.manage;

import com.telecom.base.BaseController;
import com.telecom.bean.Pager;
import com.telecom.bean.RolesBean;
import com.telecom.config.GlobalValue;
import com.telecom.manage.entity.Admin;
import com.telecom.manage.entity.Role;
import com.telecom.manage.service.AdminRoleService;
import com.telecom.manage.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "role")
public class RoleController extends BaseController {

	@Autowired
	RoleService roleService;
	
	@Autowired
	AdminRoleService adminRoleService;

	// 列表
	@GetMapping("list")
	public String list(Pager pager, ModelMap model) {
		pager = roleService.findPager(pager);
		model.put("pager", pager);
		return "/manage/role_list";
	}
	
	// 添加
	@GetMapping("add")
	public String add(ModelMap model) {
		model.put("isAdd", true);
		return "/manage/role_input";
	}

	// 编辑
	@GetMapping("edit/{id}")
	public String edit(@PathVariable(value="id") String id, ModelMap model) {
		Role role = roleService.get(id);
		model.put("role", role);
		model.put("isAdd", false);
		return "/manage/role_input";
	}

	// 删除
	@PostMapping("delete")
	@ResponseBody
	public String delete(@RequestParam(value="ids") String[] ids) {
		try {
			for (String id : ids) {
				Role role = roleService.get(id);
				List<Admin> adminList = adminRoleService.getAdminList(id);
				if (adminList != null && adminList.size() > 0) {
					return ajax(Status.error, "角色[" + role.getName() + "]下存在用户,删除失败!");
				}
			}
			
			// 遍历删除
			for (String id : ids) {
				roleService.delete(id);
			}
			
			return ajax(Status.success, "删除成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	// 保存
	@PostMapping("save")
	public String save(Role role, ModelMap model) throws Exception {
		List<String> authorityList = role.getAuthorityList();
		authorityList.add(Role.ROLE_BASE);
		role.setAuthorityList(authorityList);
		role = roleService.save(role);

		setSuccess(model, null, "role/list");
		return SUCCESS;
	}

	// 更新
	@PostMapping("update")
	public String update(@RequestParam(value="id") String id, Role role, ModelMap model) throws Exception {
		Role persistent = roleService.get(id);
		List<String> authorityList = role.getAuthorityList();
		authorityList.add(Role.ROLE_BASE);
		role.setAuthorityList(authorityList);
		persistent.setName(role.getName());
		persistent.setDescription(role.getDescription());
		persistent.setAuthorityList(role.getAuthorityList());
		roleService.update(persistent);
		
		setSuccess(model, null, "role/list");
		return SUCCESS;
	}
	
	// 获取权限集合
	@ModelAttribute
	public void getAuthRoles(ModelMap model) {
		List<RolesBean> authRoles = GlobalValue.authRoles;
		model.addAttribute("authRoles", authRoles);
	}
}
