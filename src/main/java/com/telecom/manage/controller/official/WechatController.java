package com.telecom.manage.controller.official;

import com.telecom.base.BaseController;
import com.telecom.config.OfficialConfig;
import com.telecom.official.util.MessageUtil;
import com.telecom.official.util.SignUtil;
import com.telecom.manage.service.WechatReplyService;
import com.telecom.manage.service.WechatService;
import com.telecom.manage.service.WechatUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 微信公众号核心控制器
 * PS：此类为RestController类，无页面渲染
 *
 */
@RestController
@RequestMapping(value = "wechat")
public class WechatController extends BaseController {

	@Autowired
	WechatReplyService wechatReplyService;

	@Autowired
	WechatUserService wechatUserService;

	@Autowired
	WechatService wechatService;

	@Autowired
	OfficialConfig officialConfig;

	/**
	 * 确认请求来自微信服务器
	 * 
	 */
	@GetMapping
	public String doGet() {
		try {
			// 微信加密签名、时间戳、随机数、随机字符串
			String signature = request.getParameter("signature");
			String timestamp = request.getParameter("timestamp");
			String nonce = request.getParameter("nonce");
			String echostr = request.getParameter("echostr");

			// 获取微信公众号Token
			String token = officialConfig.getToken();

			// 通过检验signature对请求进行校验
			// 若校验成功则原样返回echostr，表示接入成功
			// 若校验失败则表示接入失败
			if (SignUtil.checkSignature(token, signature, timestamp, nonce)) {
				return echostr;
			} else return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 处理微信服务器发来的消息
	 * 
	 */
	@PostMapping
	public String doPost() {
		
		// 调用核心业务类接收消息、处理消息
		String respMessage = null;
		
		try {
			// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");

			
			// 默认返回的文本消息内容，xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			// 事件类型
			String eventType = requestMap.get("Event");
			// 事件Key
			String eventKey = requestMap.get("EventKey");
						
			// 情况1：内容型
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)
					|| msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)
					|| msgType.equals(MessageUtil.RESP_MESSAGE_TYPE_NEWS)) {
				respMessage = wechatService.contentOperate(requestMap, fromUserName,
						toUserName, msgType);
			}

			// 情况2：事件型
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				respMessage = wechatService.eventOperate(eventType, eventKey, fromUserName,
						toUserName);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return respMessage;
	}

}
