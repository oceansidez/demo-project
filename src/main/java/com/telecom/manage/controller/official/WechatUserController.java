package com.telecom.manage.controller.official;

import com.telecom.base.BaseController;
import com.telecom.bean.Pager;
import com.telecom.manage.entity.WechatUser;
import com.telecom.manage.service.WechatUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * 微信用户
 *
 */
@Controller
@RequestMapping(value = "wechatUser")
public class WechatUserController extends BaseController {

	@Autowired
	WechatUserService wechatUserService;

	private final String PAGE_PRE = "/official/wechat_user_";
	
	/**
	 * 列表
	 * 
	 * @param pager
	 * @param model
	 * @return
	 */
	@GetMapping("list")
	public String list(Pager pager, ModelMap model) {
		pager = wechatUserService.findPager(pager);
		model.put("pager", pager);
		return PAGE_PRE + "list";
	}

	/**
	 * 冻结/激活用户
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@ResponseBody
	@PostMapping("switch/{id}")
	public String switchItem(@PathVariable(value = "id") String id,
			ModelMap model) {
		WechatUser user = wechatUserService.get(id);
		if (user.getIsLock()) {
			user.setIsLock(false);
		} else {
			user.setIsLock(true);
		}
		wechatUserService.update(user);
		return ajax(Status.success, "操作成功");
	}

	/**
	 * 删除用户
	 * 
	 * @param ids
	 * @return
	 */
	@PostMapping("delete")
	public String delete(@RequestParam(value = "ids") String[] ids) {
		for (String id : ids) {
			wechatUserService.delete(id);
		}
		return ajax(Status.success, "删除成功!");
	}
}
