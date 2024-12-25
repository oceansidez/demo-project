package com.telecom.manage.controller.web;

import com.telecom.base.BaseController;
import com.telecom.config.OfficialConfig;
import com.telecom.official.WechatOfficialCore;
import com.telecom.official.util.MessageUtil;
import com.telecom.manage.service.WechatService;
import com.telecom.manage.service.WechatUserService;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;

/**
 * Web端 微信公众号Wap页
 *
 */
@Controller
@RequestMapping(value = "/web/wechat")
public class WechatWebController extends BaseController {

	private final static String PAGE_PRE = "/web/wechat_";

	@Autowired
	WechatService wechatService;
	
	@Autowired
	WechatUserService wechatUserService;

	@Autowired
	OfficialConfig officialConfig;

	/**
	 * 微信静默授权
	 */
	@GetMapping("base")
	public String base(@RequestParam(value="code", required=false) String code, ModelMap model) {
		try {
			// 若code为空，则需要调用接口获取授权code
			if(StringUtils.isEmpty(code)){
				// 重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
				// 此处可将customParam写入state以供使用，具体需要传入的参数由业务决定
				String customParam = "123";
				
				// 获取当前请求地址作为回调地址参数redirect_uri的值
				String requestUrl = request.getRequestURL().toString();
				if (StringUtils.isNotEmpty(request.getQueryString())) {
					requestUrl = requestUrl + "?" + request.getQueryString();
				}
				requestUrl = URLEncoder.encode(requestUrl, "UTF-8");
				
				// 获取请求地址
				String url = WechatOfficialCore.AUTHORIZE_GET_CODE_URL
						.replace("APPID", officialConfig.getAppId())
						.replace("REDIRECT_URI", requestUrl)
						.replace("SCOPE", WechatOfficialCore.AUTHORIZE_BASE)
						.replace("STATE", customParam);
				// 重定向
				return "redirect:" + url;
			}
			// 若code不为空，则视为已回调code
			else{
				// 使用code换取网页授权专用token
				JSONObject obj = WechatOfficialCore.getAuthorizeAccessToken(
						officialConfig.getAppId(), officialConfig.getAppSecret(), code);
				String openId = obj.getString("openid");
				
				// 业务处理
				//（静默授权时，只能拿到openid，要获取用户信息，必须调用拉取用户信息接口，此接口还需要通用accessToken）
				// ...
				String accessToken = wechatService.getAccessToken().getToken();
				JSONObject userObj = WechatOfficialCore.getUserInfo(openId, accessToken);
				String username = MessageUtil.filterEmoji(userObj.getString("nickname"));
				model.put("openId", openId);
				model.put("username", username);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return PAGE_PRE + "base_example";
	}

	/**
	 * 微信手动授权
	 */
	@GetMapping("userinfo")
	public String userinfo(@RequestParam(value="code", required=false) String code, ModelMap model) {
		try {
			// 若code为空，则需要调用接口获取授权code
			if(StringUtils.isEmpty(code)){
				// 重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
				// 此处可将customParam写入state以供使用，具体需要传入的参数由业务决定
				String customParam = "123";
				
				// 获取当前请求地址作为回调地址参数redirect_uri的值
				String requestUrl = request.getRequestURL().toString();
				if (StringUtils.isNotEmpty(request.getQueryString())) {
					requestUrl = requestUrl + "?" + request.getQueryString();
				}
				requestUrl = URLEncoder.encode(requestUrl, "UTF-8");
				
				// 获取请求地址
				String url = WechatOfficialCore.AUTHORIZE_GET_CODE_URL
						.replace("APPID", officialConfig.getAppId())
						.replace("REDIRECT_URI", requestUrl)
						.replace("SCOPE", WechatOfficialCore.AUTHORIZE_USER)
						.replace("STATE", customParam);
				// 重定向
				return "redirect:" + url;
			}
			// 若code不为空，则视为已回调code
			else{
				// 使用code换取网页授权专用token
				JSONObject obj = WechatOfficialCore.getAuthorizeAccessToken(
						officialConfig.getAppId(), officialConfig.getAppSecret(), code);
				String openId = obj.getString("openid");
				String authorizeAccessToken = obj.getString("access_token");
				
				// 业务处理
				//（手动授权时，通过openid与授权专用token可调用专用拉取用户接口，而无需获取通用accessToken再调用其他接口）
				// ...
				JSONObject userObj = WechatOfficialCore.getAuthorizeUserInfo(authorizeAccessToken, openId);
				String username = MessageUtil.filterEmoji(userObj.getString("nickname"));
				model.put("openId", openId);
				model.put("username", username);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return PAGE_PRE + "userinfo_example";
	}

}
