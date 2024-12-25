package com.telecom.manage.controller.manage;

import com.telecom.base.BaseController;
import com.telecom.bean.Pager;
import com.telecom.config.WebConfig;
import com.telecom.manage.entity.Admin;
import com.telecom.manage.entity.Role;
import com.telecom.manage.service.AdminRoleService;
import com.telecom.manage.service.AdminService;
import com.telecom.manage.service.RoleService;
import com.telecom.util.FileUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "admin")
public class AdminController extends BaseController {

	@Autowired
	WebConfig webConfig;
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	AdminRoleService adminRoleService;
	
	// 是否已存在username --- ajax验证
	@GetMapping("checkUsername")
	@ResponseBody
	public String checkUsername(String username) {
		if (adminService.isExistByUsername(username)) {
			return ajax("false");
		} else {
			return ajax("true");
		}
	}
	
	// 当前密码是否正确  --- ajax验证
	@GetMapping("checkCurrentPassword")
	@ResponseBody
	public String checkCurrentPassword(String currentPassword) {
		Admin admin = Admin.getLoginAdmin(request.getSession());
		if (StringUtils.equals(DigestUtils.md5Hex(currentPassword), admin.getPassword())) {
			return ajax("true");
		} else {
			return ajax("false");
		}
	}
	
	// 列表
	@GetMapping("list")
	public String list(Pager pager, ModelMap model) {
		pager = adminService.findPager(pager);
		model.put("pager", pager);
		return "/manage/admin_list";
	}
	
	// 添加
	@GetMapping("add")
	public String add(ModelMap model) {
		model.put("isAdd", true);
		return "/manage/admin_input";
	}

	// 编辑
	@GetMapping("edit/{id}")
	public String edit(@PathVariable(value="id") String id, ModelMap model) {
		Admin admin = adminService.get(id);
		model.put("isAdd", false);
		model.put("admin", admin);
		model.put("roleSet", adminRoleService.getRoleList(admin.getId()));
		return "/manage/admin_input";
	}
	
	// 查看
	@GetMapping("view/{id}")
	public String view(@PathVariable(value="id") String id, ModelMap model) {
		Admin admin = adminService.get(id);
		model.put("admin", admin);
		return "/manage/admin_view";
	}
	
	// 删除
	@PostMapping("delete")
	@ResponseBody
	public String delete(@RequestParam(value="ids") String[] ids) {
		for (String id : ids) {
			adminRoleService.deleteAdminRoleByAdmin(id);
			adminService.delete(id);
		}
		return ajax(Status.success, "删除成功!");
	}

	// 保存
	@PostMapping("save")
	public String save(
			@RequestParam(value="roleIds",required=false) String[] roleIds,
			@RequestParam(value="rePassword",required=true) String rePassword,
			Admin admin, ModelMap model) {
		if (adminService.isExistByUsername(admin.getUsername())) {
			setError(model, "用户已存在", null);
			return ERROR;
		}
		
		if(roleIds == null || roleIds.length <= 0){
			setError(model, "请选择至少一个角色", null);
			return ERROR;
		}
		
		admin.setCreateDate(new Date());
		admin.setModifyDate(new Date());
		admin.setLoginFailureCount(0);
		admin.setPassword(DigestUtils.md5Hex(admin.getPassword()));
		admin = adminService.save(admin);
		
		List<String> roleIdList = Arrays.asList(roleIds);
		adminRoleService.batchInsertAdminRole(admin.getId(), roleIdList);
		
		setSuccess(model, null, "admin/list");
		return SUCCESS;
	}

	// 更新
	@PostMapping("update")
	public String update(
			@RequestParam(value="id") String id,
			@RequestParam(value="roleIds",required=false) String[] roleIds,
			@RequestParam(value="rePassword",required=false) String rePassword,
			Admin admin, ModelMap model) {
		if(roleIds == null || roleIds.length <= 0){
			setError(model, "请选择至少一个角色", null);
			return ERROR;
		}
		
		Admin persistent = adminService.get(id);
		persistent.setName(admin.getName());
		persistent.setMobile(admin.getMobile());
		persistent.setIsLock(admin.getIsLock());
		if(StringUtils.isNotEmpty(admin.getPassword()) && 
		   StringUtils.isNotEmpty(rePassword) &&
		    admin.getPassword().equals(rePassword)){
			persistent.setPassword(DigestUtils.md5Hex(admin.getPassword()));
		}
		adminService.update(persistent);
		
		adminRoleService.deleteAdminRoleByAdmin(id);
		List<String> roleIdList = Arrays.asList(roleIds);
		adminRoleService.batchInsertAdminRole(admin.getId(), roleIdList);
		
		setSuccess(model, null, "admin/list");
		return SUCCESS;
	}
	
	// 个人资料
	@GetMapping("profile")
	public String profileView(ModelMap model) {
		// Admin.getLoginAdmin获取的登录用户包含security信息
		// 若DA层处理数据后，数据有更新，则无法显示最新数据，所以需要重新get获取
		Admin admin = Admin.getLoginAdmin(request.getSession());
		admin = adminService.get(admin.getId());
		model.put("admin", admin);
		return "/manage/admin_profile";
	}
	
	// 个人资料更新
	@PostMapping("profile/update")
	public String profileUpdate(Admin admin,
			@RequestParam(value="file") MultipartFile file,
			@RequestParam(value="currentPassword") String currentPassword,
			@RequestParam(value="rePassword") String rePassword,
			ModelMap model) {
		// Admin.getLoginAdmin获取的登录用户包含security信息
		// 在DA层处理会出现异常，所以需要重新get获取
		Admin persistent = Admin.getLoginAdmin(request.getSession());
		persistent = adminService.get(persistent.getId());
		if (StringUtils.isNotEmpty(currentPassword) && StringUtils.isNotEmpty(admin.getPassword())) {
			if (!StringUtils.equals(DigestUtils.md5Hex(currentPassword), persistent.getPassword())) {
				setError(model, "当前密码输入错误!", null);
				return ERROR;
			}
			if(!admin.getPassword().equals(rePassword)){
				setError(model, "两次输入密码不一致", null);
				return ERROR;
			}
			persistent.setPassword(DigestUtils.md5Hex(admin.getPassword()));
		}
		
		try {
			FileUtil.uploadFile(file, webConfig.getUploadPath() + "/file/", file.getOriginalFilename());
			persistent.setHeadImg("/file/" + file.getOriginalFilename());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		persistent.setMobile(admin.getMobile());
		adminService.update(persistent);
		setSuccess(model, null, "page/index");
		return SUCCESS;
	}
	
	// 获取展示地址
	@ModelAttribute
	public void getViewUrl(ModelMap model) {
		model.addAttribute("viewUrl", webConfig.getViewPath());
	}
	
	// 获取FTP地址
	@ModelAttribute
	public void getFtpUrl(ModelMap model) {
		model.addAttribute("ftpUrl", webConfig.getFtp());
	}
	
	// 获取角色列表
	@ModelAttribute
	public void getRoleList(ModelMap model) {
		List<Role> list = roleService.getAllList();
		model.addAttribute("roleList", list);
	}
}
