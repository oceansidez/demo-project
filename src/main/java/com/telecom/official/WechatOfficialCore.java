package com.telecom.official;

import com.telecom.official.pojo.*;
import com.telecom.official.util.MyX509TrustManager;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 公众平台通用接口工具类
 * 
 */
public class WechatOfficialCore {
	
	private static Logger log = LoggerFactory.getLogger(WechatOfficialCore.class);

	// 获取公众号用户信息（GET）
	public final static String INFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
	
	// 获取access_token的接口地址（GET） 限200（次/天）
	public final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	// 获取access_token的接口地址---企业版（GET） 限200（次/天）
	public final static String ACCESS_TOKEN_URL_QY = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=CORPID&corpsecret=CORPSECRET";

	// 菜单查询接口地址（GET）
	public final static String MENU_QUERY_URL = "https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info?access_token=ACCESS_TOKEN";
	
	// 菜单创建接口地址（POST） 限100（次/天）
	public final static String MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	// 菜单创建接口地址---企业版（POST） 限100（次/天）
	public final static String MENU_CREATE_URL_QY = "https://qyapi.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN&agentid=AGENTID";

	// 获取第三方开放平台的access_token的接口地址（POST）
	public final static String ACCESS_TOKEN_URL_COMPONENT = "https://api.weixin.qq.com/cgi-bin/component/api_component_token";

	// 获取第三方开放平台的预授权码pre_auth_code（POST）
	public final static String PRE_AUTH_CODE_URL = "https://api.weixin.qq.com/cgi-bin/component/api_create_preauthcode?component_access_token=COMPONENT_ACCESS_TOKEN";

	// 微信网页授权获取code（GET）
	public final static String AUTHORIZE_GET_CODE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
	public final static String AUTHORIZE_BASE = "snsapi_base"; // 静默授权
	public final static String AUTHORIZE_USER = "snsapi_userinfo"; // 手动授权
	
	// 微信网页授权获取access_token（网页授权专用token）（GET）
	public final static String AUTHORIZE_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	
	// 微信网页授权拉取用户信息（手动授权snsapi_userinfo时使用）（GET）
	public final static String AUTHORIZE_USER_INFO_QUERY_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
		
	
	/**
	 * 获取公众号用户信息
	 * @param openId
	 * @param accessToken
	 * @return
	 */
	public static JSONObject getUserInfo(String openId, String accessToken) {
		String requestUrl = INFO_URL.
				replace("OPENID", openId).
				replace("ACCESS_TOKEN", accessToken);
		
		JSONObject object = WechatOfficialCore.httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != object) {
			return object;
		} else {
			System.out.println("获取失败");
			return null;
		}
	}
	
	/**
	 * 获取access_token
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return
	 */
	public static AccessToken getAccessToken(String appid, String appsecret) {
		AccessToken accessToken = null;

		String requestUrl = ACCESS_TOKEN_URL.replace("APPID", appid).replace(
				"APPSECRET", appsecret);
		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
				System.out.println("成功获取accessToken:" + accessToken.getToken());
			} catch (JSONException e) {
				accessToken = null;
				// 获取token失败
				log.error("获取token失败 errcode:{} errmsg:{}",
						jsonObject.getInt("errcode"),
						jsonObject.getString("errmsg"));
			}
		} else {
			System.out.println("未能取得accessToken，因为jsonObject=" + jsonObject);
		}
		return accessToken;
	}
	
	/**
	 * 获取企业access_token
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return
	 */
	public static AccessToken getAccessTokenByQy(String corpid,
			String corpsecret) {
		AccessToken accessToken = null;

		String requestUrl = ACCESS_TOKEN_URL_QY.replace("CORPID", corpid)
				.replace("CORPSECRET", corpsecret);
		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
			} catch (JSONException e) {
				accessToken = null;
				// 获取token失败
				log.error("获取token失败 errcode:{} errmsg:{}",
						jsonObject.getInt("errcode"),
						jsonObject.getString("errmsg"));
			}
		}
		return accessToken;
	}

	/**
	 * 获取第三方开放平台access_token
	 * 
	 * @param appid
	 *            第三方平台appid
	 * @param appsecret
	 *            第三方平台appsecret
	 * @param ticket
	 *            微信后台推送的ticket，此ticket会定时推送
	 * @return
	 */
	public static AccessToken getAccessTokenByComponent(String appid,
			String appsecret, String ticket) {
		AccessToken accessToken = null;
		String param = "{\"component_appid\":\"" + appid
				+ "\",\"component_appsecret\":\"" + appsecret
				+ "\",\"component_verify_ticket\":\"" + ticket + "\"}";
		System.out.println("获取accesstoken请求参数为：" + param);
		JSONObject jsonObject = httpsRequest(ACCESS_TOKEN_URL_COMPONENT, "POST",
				param);
		// 如果请求成功
		System.out.println("获取accessToken结果为：" + jsonObject.toString());
		if (null != jsonObject) {
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject
						.getString("component_access_token"));
				accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
			} catch (JSONException e) {
				accessToken = null;
				// 获取token失败
				log.error("获取token失败 errcode:{} errmsg:{}",
						jsonObject.getInt("errcode"),
						jsonObject.getString("errmsg"));
			}
		}
		return accessToken;
	}

	/**
	 * 查询菜单
	 * @param accessToken
	 * 			 有效的access token
	 * @return 
	 */
	public static List<WechatMenu> queryMenu(String accessToken) {
		// 拼装创建菜单的url
		String url = MENU_QUERY_URL.replace("ACCESS_TOKEN", accessToken);
		// 调用接口创建菜单
		JSONObject jsonObject = httpsRequest(url, "GET", null);
		
		if (null != jsonObject) {
			if(1 == jsonObject.getInt("is_menu_open")){
				JSONObject menu = jsonObject.getJSONObject("selfmenu_info");
				return convertToMenu(menu);
			}
			else{
				return null;
			}
		}
		else return null;
	}
	
	/**
	 * 创建菜单
	 * 
	 * @param menu
	 *            菜单实例
	 * @param accessToken
	 *            有效的access_token
	 * @return 0表示成功，其他值表示失败
	 */
	public static int createMenu(String menuString, String accessToken) {
		int result = 0;

		// 拼装创建菜单的url
		String url = MENU_CREATE_URL.replace("ACCESS_TOKEN", accessToken);
		// 将菜单对象转换成json字符串
		String jsonMenu = JSONObject.fromObject(packageMenu(menuString)).toString();
		// 调用接口创建菜单
		JSONObject jsonObject = httpsRequest(url, "POST", jsonMenu);

		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				log.error("创建菜单失败 errcode:{} errmsg:{}",
						jsonObject.getInt("errcode"),
						jsonObject.getString("errmsg"));
			}
		}

		return result;
	}

	/**
	 * 创建企业号菜单
	 * 
	 * @param menu
	 *            菜单实例
	 * @param accessToken
	 *            有效的access_token
	 * @return 0表示成功，其他值表示失败
	 */
	public static int createMenuByQy(Menu menu, String accessToken,
			Integer agentid) {
		int result = 0;

		// 拼装创建菜单的url
		String url = MENU_CREATE_URL_QY.replace("ACCESS_TOKEN", accessToken)
				.replace("AGENTID", agentid + "");
		// 将菜单对象转换成json字符串
		String jsonMenu = JSONObject.fromObject(menu).toString();
		// 调用接口创建菜单
		JSONObject jsonObject = httpsRequest(url, "POST", jsonMenu);

		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				log.error("创建菜单失败 errcode:{} errmsg:{}",
						jsonObject.getInt("errcode"),
						jsonObject.getString("errmsg"));
			}
		}

		return result;
	}
	
	/**
	 * 获取预授权码。预授权码用于公众号授权时的第三方平台方安全验证。
	 * 
	 * @param componentAppid
	 *            第三方平台appid
	 * @param componentAccessToken
	 *            第三方平台accessToken
	 * @return
	 */
	public static PreAuthCode getPreAuthCode(String componentAppid,
			String componentAccessToken) {
		PreAuthCode preAuthCode = null;
		String param = "{\"component_appid\":\"" + componentAppid + "\"}";
		String requestUrl = PRE_AUTH_CODE_URL.replace("COMPONENT_ACCESS_TOKEN",
				componentAccessToken);
		JSONObject jsonObject = httpsRequest(requestUrl, "POST", param);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				preAuthCode = new PreAuthCode();
				preAuthCode.setPreAuthcode(jsonObject
						.getString("pre_auth_code"));
				preAuthCode.setExpiresIn(jsonObject.getInt("expires_in"));
			} catch (JSONException e) {
				preAuthCode = null;
				// 获取token失败
				log.error("获取预授权码preAuthCode失败 errcode:{} errmsg:{}",
						jsonObject.getInt("errcode"),
						jsonObject.getString("errmsg"));
			}
		}
		return preAuthCode;
	}
	
	/**
	 * 获取网页授权专用Token
	 * @param appid
	 * @param secret
	 * @param code
	 * @return
	 */
	public static JSONObject getAuthorizeAccessToken(String appid, String secret, String code){
		String requestUrl = AUTHORIZE_ACCESS_TOKEN_URL
				.replace("APPID", appid)
				.replace("SECRET", secret)
				.replace("CODE", code);
		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				return jsonObject;
			} catch (Exception e) {
				// 获取token失败
				log.error("获取网页授权专用token失败 errcode:{} errmsg:{}",
						jsonObject.getInt("errcode"),
						jsonObject.getString("errmsg"));
				return null;
			}
		}
		else return null;
	}
	
	/**
	 * 网页授权（手动授权模式）获取用户信息
	 * @param authorizeAccessToken网页授权专用Token
	 * @param openId
	 * @return
	 */
	public static JSONObject getAuthorizeUserInfo(String authorizeAccessToken, String openId){
		String requestUrl = AUTHORIZE_USER_INFO_QUERY_URL
				.replace("ACCESS_TOKEN", authorizeAccessToken)
				.replace("OPENID", openId);
		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			return jsonObject;
		} else {
			System.out.println("获取失败");
			return null;
		}
	}
	
	/** 
     * 组装菜单数据 
     * @param menuString
     * @return 
     */  
	@SuppressWarnings("unchecked")
	private static Menu packageMenu(String menuString) {
		List<WechatMenu> menus = new ArrayList<WechatMenu>();
		
		// 由于存在XSS防护机制，可能出现被转义成全角双引号的情况，需要额外转义回来
		// 即使不开启XSS防护，该代码也不影响原有内容
		menuString = menuString.replaceAll("＂", "\"");
		
		JSONObject jsonobject = JSONObject.fromObject(menuString);
		JSONArray array = jsonobject.getJSONArray("menus");
		for (int i = 0; i < array.size(); i++) {
			JSONObject object = (JSONObject) array.get(i);
			WechatMenu menu = (WechatMenu) JSONObject.toBean(object,
					WechatMenu.class);
			if (object.get("items") != null) {
				JSONArray items = object.getJSONArray("items");
				List<WechatMenu> list = (List<WechatMenu>) JSONArray
						.toCollection(items, WechatMenu.class);
				menu.setItems(list);
			}
			if (menu != null) {
				menus.add(menu);
			}
		}
		
		Menu menu = new Menu();
		Button[] button = new Button[menus.size()];
		for (int i = 0; i < menus.size(); i++) {
			WechatMenu wmb = menus.get(i);
			// 链接型菜单
			if (wmb.getType().equals("view")) {
				ViewButton btn = new ViewButton();
				btn.setName(wmb.getName());
				btn.setType("view");
				btn.setUrl(wmb.getUrl());
				button[i] = btn;
			} 
			// 点击事件型菜单
			else if (wmb.getType().equals("click")) {
				CommonButton btn = new CommonButton();
				btn.setName(wmb.getName());
				btn.setType("click");
				btn.setKey(wmb.getKey());
				button[i] = btn;
			}
			// 子菜单
			else if (wmb.getType().equals("father")) {
				ComplexButton mainBtn = new ComplexButton();
				mainBtn.setName(wmb.getName());
				Button[] subBtn = new Button[wmb.getItems().size()];
				for (int j = 0; j < wmb.getItems().size(); j++) {
					WechatMenu sub = wmb.getItems().get(j);
					if (sub.getType().equals("view")) {
						ViewButton btn = new ViewButton();
						btn.setName(sub.getName());
						btn.setType("view");
						btn.setUrl(sub.getUrl());
						subBtn[j] = btn;
					} else if (sub.getType().equals("click")) {
						CommonButton btn = new CommonButton();
						btn.setName(sub.getName());
						btn.setType("click");
						btn.setKey(sub.getKey());
						subBtn[j] = btn;
					}
				}
				mainBtn.setSub_button(subBtn);
				button[i] = mainBtn;
			}
		}
		menu.setButton(button);
		return menu;
    }
	
	/**
	 * 转换菜单数据（用于展示）
	 * @param menuJson
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static List<WechatMenu> convertToMenu(JSONObject result) {
		List<WechatMenu> menus = new ArrayList<WechatMenu>();
		if(result != null) {
			JSONArray menuArray = result.getJSONArray("button");
			for (int i = 0; i < menuArray.size(); i++) {
				JSONObject menuJson = menuArray.getJSONObject(i);
				WechatMenu menu = new WechatMenu();
				menu.setIndex(i);
				menu.setName(menuJson.get("name").toString());
				if(menuJson.get("type") != null && menuJson.get("type").toString().equals("view")) {
					menu.setType(menuJson.get("type").toString());
					menu.setValue(menuJson.get("url").toString());
				}else if(menuJson.get("type") != null && menuJson.get("type").toString().equals("click")) {
					menu.setType(menuJson.get("type").toString());
					menu.setValue(menuJson.get("key").toString());
				}
				else{
					menu.setType("father");
				}
				if (menuJson.get("sub_button") != null) {
					JSONObject subButton = menuJson.getJSONObject("sub_button");
					JSONArray items = subButton.getJSONArray("list");
					List<WechatMenu> list = (List<WechatMenu>) JSONArray.toCollection(items, WechatMenu.class);
					if(list!=null && list.size()>0) {
						for (WechatMenu childMenu : list) {
							if(childMenu.getType().equals("view")) {
								childMenu.setValue(childMenu.getUrl());
							}else if(childMenu.getType().equals("click")) {
								childMenu.setValue(childMenu.getKey());
							}
						}
					}
					menu.setItems(list);
				}
				if (menu != null) {
					menus.add(menu);
				}
	 		}	
		}
		return menus;
	}
	
	/**
	 * 发起http请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	private static JSONObject httpRequest(String requestUrl, String requestMethod,
			String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
					.openConnection();
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);
			httpUrlConn.setRequestProperty("Accept", "application/json");
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
			log.error("Weixin server connection timed out.");
		} catch (Exception e) {
			log.error("https request error:{}", e);
		}
		return jsonObject;
	}

	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	private static JSONObject httpsRequest(String requestUrl,
			String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager()};
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
					.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
			log.error("Weixin server connection timed out.");
		} catch (Exception e) {
			log.error("https request error:{}", e);
		}
		return jsonObject;
	}
	
}