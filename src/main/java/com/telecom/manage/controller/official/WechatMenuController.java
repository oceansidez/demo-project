package com.telecom.manage.controller.official;

import com.telecom.base.BaseController;
import com.telecom.config.OfficialConfig;
import com.telecom.official.WechatOfficialCore;
import com.telecom.official.pojo.AccessToken;
import com.telecom.official.pojo.WechatMenu;
import com.telecom.manage.service.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 微信菜单配置
 *
 * 1、自定义菜单最多包括3个一级菜单，每个一级菜单最多包含5个二级菜单。
 * 2、一级菜单最多4个汉字，二级菜单最多7个汉字，多出来的部分将会以“...”代替。
 * 3、创建自定义菜单后，菜单的刷新策略是，在用户进入公众号会话页或公众号profile页时，
 *    如果发现上一次拉取菜单的请求在5分钟以前，就会拉取一下菜单，如果菜单有更新，就会刷新客户端的菜单。
 *    测试时可以尝试取消关注公众账号后再次关注，则可以看到创建后的效果。
 *
 */
@Controller
@RequestMapping(value = "wechatConfig")
public class WechatMenuController extends BaseController {

	@Autowired
	WechatService wechatService;
	
	@Autowired
	OfficialConfig officialConfig;
	
	private final static String PAGE_PRE = "/official/wechat_";
	
	/**
	 * 微信菜单页面
	 */
	@GetMapping("index")
	public String index(ModelMap model){
		model.put("officialAccount", officialConfig.getAccount());
		// 调用接口获取access_token
		AccessToken accessToken = wechatService.getAccessToken();
		if (accessToken != null) {
			// 调用接口创建菜单
			List<WechatMenu> list = WechatOfficialCore.queryMenu(accessToken.getToken());
			model.put("menuList", list);
		}
		return PAGE_PRE + "menu";
	}
	
	/**
	 * 查询微信菜单
	 * @return
	 */
	@ResponseBody
	@GetMapping("menu")
	public String getMenu() {
		// 调用接口获取access_token
		AccessToken accessToken = wechatService.getAccessToken();
		if (accessToken != null) {
			// 调用接口创建菜单
			List<WechatMenu> list = WechatOfficialCore.queryMenu(accessToken.getToken());
			// 判断菜单创建结果
			if (null != list && list.size() > 0) {
				return ajax(list);
			} 
		}
		return null;
	}
	
	/**
	 * 创建微信菜单
	 * @param menuString
	 * @param model
	 * @return
	 */
	@PostMapping("/menu")
	public String menu(@RequestParam(value = "menuString")String menuString,
			ModelMap model){
		// 调用接口获取access_token
		AccessToken accessToken = wechatService.getAccessToken();
		if (accessToken != null) {
			// 调用接口创建菜单
			Integer result = WechatOfficialCore.createMenu(menuString,
					accessToken.getToken());
			// 判断菜单创建结果
			if (0 == result) {
				setSuccess(model, "菜单创建成功", "wechatConfig/index");
		    	return SUCCESS;
			} 
		}
		setError(model, "菜单创建失败", "wechatConfig/index");
    	return ERROR;
	}
	
}
