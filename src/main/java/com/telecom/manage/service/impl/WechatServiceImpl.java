package com.telecom.manage.service.impl;

import com.telecom.config.OfficialConfig;
import com.telecom.config.WebConfig;
import com.telecom.manage.entity.WechatReply;
import com.telecom.manage.entity.WechatReply.ReplyType;
import com.telecom.manage.entity.WechatUser;
import com.telecom.manage.entity.WechatUser.AttentionType;
import com.telecom.manage.entity.WechatUser.Sex;
import com.telecom.manage.mapper.WechatReplyMapper;
import com.telecom.manage.mapper.WechatUserMapper;
import com.telecom.manage.service.WechatService;
import com.telecom.official.WechatOfficialCore;
import com.telecom.official.WechatUploadFile;
import com.telecom.official.pojo.AccessToken;
import com.telecom.official.response.*;
import com.telecom.official.util.MessageUtil;
import com.telecom.util.DateOperateUtil;
import com.telecom.util.RedisUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WechatServiceImpl implements WechatService {

	@Autowired
	WechatUserMapper wechatUserMapper;

	@Autowired
	WechatReplyMapper wechatReplyMapper;

	@Autowired
	OfficialConfig officialConfig;

	@Autowired
	WebConfig webConfig;

	//应该可以换成@Resource
	@Autowired
	RedisUtil redisUtil;

	@Override
	public AccessToken getAccessToken() {
		//为什么不用自动注入
		AccessToken accessToken = new AccessToken();
		String appId = officialConfig.getAppId();//获取公众号的appid
		String appSecret = officialConfig.getAppSecret();//获取公众号的微信号Account
		try {
			if (redisUtil.exists(appId) && null != redisUtil.getos(appId) &&
				redisUtil.exists(appId + "_Time") && null != redisUtil.getos(appId + "_Time")) {
				accessToken = (AccessToken) redisUtil.getos(appId);
				Date tokenTime = (Date) redisUtil.getos(appId + "_Time");
				Date deadTime = DateOperateUtil.plusMinute(officialConfig.getAccessTokenExpireTime() / 60, tokenTime);
				long between = (deadTime.getTime() - new Date().getTime()) / 1000;
				// 如果redis中的过期时间距离当前时间只相差5分钟以内
				// 则重新获取，因为需要保证不出现如下情况：（假设）
				// 步骤1---判断并获取redis中的token值
				// 步骤2---业务处理
				// 步骤3---使用redis中的token值
				// 如果步骤2的处理时间超过了5分钟，可能此处判断虽然redis中的token未过期
				// 但是实际上在微信官方服务端中的token已经过期了，在步骤3时，使用该token则是无效的
				if(between < 5){
					// 重新获取accessToken
					accessToken = WechatOfficialCore.getAccessToken(appId, appSecret);
					redisUtil.setosex(appId, accessToken,
							officialConfig.getAccessTokenExpireTime());
					redisUtil.setosex(appId + "_Time", new Date(),
							officialConfig.getAccessTokenExpireTime());
				}
			} else {
				// 重新获取accessToken
				accessToken = WechatOfficialCore.getAccessToken(appId, appSecret);
				redisUtil.setosex(appId, accessToken,
						officialConfig.getAccessTokenExpireTime());
				redisUtil.setosex(appId + "_Time", new Date(),
						officialConfig.getAccessTokenExpireTime());
			}
		} catch (Exception e) {
			System.out.println("获取accessToken异常：" + e.getMessage());
		}
		return accessToken;
	}

	@Override
	public String contentOperate(Map<String, String> requestMap,
			String fromUserName, String toUserName, String msgType) {
		// 获取用户发送的信息（视为关键字）
		String content = "";
		if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
			content = requestMap.get("Content").trim();
		}

		// 查询匹配到的默认回复
		Map<String, Object> replyMap = new HashMap<String, Object>();
		replyMap.put("type", ReplyType.defaults.toString());
		List<WechatReply> defaultReplies = wechatReplyMapper
				.getListByConditions(replyMap);

		// 查询匹配到的关键字回复
		replyMap.put("type", ReplyType.keyword.toString());
		replyMap.put("keyword", content);
		List<WechatReply> keyReplies = wechatReplyMapper
				.getListByConditions(replyMap);

		// 获取AccessToken
		String accessToken = getAccessToken().getToken();
		
		// 获取回复内容（先判断关键字回复列表，若关键字回复列表为空，则使用默认回复）
		if (keyReplies != null && keyReplies.size() > 0) {
			return replyMsg(fromUserName, toUserName, accessToken, keyReplies);
		} else if (defaultReplies != null && defaultReplies.size() > 0) {
			return replyMsg(fromUserName, toUserName, accessToken, defaultReplies);
		} else {
			return "success";
		}
	}

	@Override
	public String eventOperate(String eventType, String eventKey,
			String fromUserName, String toUserName) {
		
		// 获取AccessToken
		String accessToken = getAccessToken().getToken();
		// 定义响应内容
		String responseContent = null;
		// 根据事件类型进行处理
		switch (eventType) {
			// 订阅关注
			case MessageUtil.EVENT_TYPE_SUBSCRIBE:
				responseContent = subscribeEvent(accessToken, eventKey, fromUserName, toUserName);break;
			// 取消订阅关注
			case MessageUtil.EVENT_TYPE_UNSUBSCRIBE:
				responseContent = unsubscribeEvent(fromUserName, toUserName);break;
			// 扫描带参数二维码事件
			// 用户扫描带场景值二维码时，可能推送以下两种事件：
			// 如果用户还未关注公众号，则用户可以关注公众号，关注后微信会将带场景值关注事件推送给开发者。
			// 如果用户已经关注公众号，则微信会将带场景值扫描事件推送给开发者。
			case MessageUtil.EVENT_TYPE_SCAN:
				responseContent = scanEvent(accessToken, eventKey, fromUserName, toUserName);break;
			// 自定义点击拉取消息事件
			case MessageUtil.EVENT_TYPE_CLICK:
				responseContent = clickEvent(accessToken, eventKey, fromUserName, toUserName);break;
			// 自定义点击跳转链接事件（无需向用户回复）
			case MessageUtil.EVENT_TYPE_VIEW:
				viewEvent(accessToken, eventKey, fromUserName, toUserName);break;
			default: break;
		}
		return responseContent;
	}
	
	// 微信用户初始化
	private WechatUser initUser(String accessToken, String openId){
    	try {
			JSONObject object = WechatOfficialCore.getUserInfo(openId, accessToken);
			if (object != null) {
				String nickname = MessageUtil.filterEmoji(object.getString("nickname"));
				String sex = object.getString("sex");
				String city = object.getString("city");
				String province = object.getString("province");
				String country = object.getString("country");
				String headimgurl = object.getString("headimgurl");
				WechatUser wechatUser = wechatUserMapper.findByOpenId(openId);
				if (wechatUser != null) {
					wechatUser.setIsLock(false);
					if (sex.equals("1")) {
						wechatUser.setSex(Sex.male.toString());
					} else {
						wechatUser.setSex(Sex.female.toString());
					}
					wechatUser.setCountry(country);
					wechatUser.setProvince(province);
					wechatUser.setCity(city);
					wechatUser.setOpenId(openId);
					wechatUser.setNick(nickname);
					wechatUser.setPicUrl(headimgurl);
					wechatUser.setLoginLastTime(new Date());
					if (wechatUser.getAttentionType().equals(AttentionType.unsubscribe.toString())) {
						wechatUser.setAttentionType(AttentionType.again.toString());
						System.out.println("用户" + nickname + "再关注,openId:" + openId);
					} else {
						wechatUser.setAttentionType(AttentionType.concerned.toString());
						System.out.println("用户" + nickname + "已关注,openId:" + openId);
					}
					wechatUserMapper.updateByPrimaryKey(wechatUser);
				} else {
					wechatUser = new WechatUser();
					wechatUser.setIsLock(false);
					if (sex.equals("1")) {
						wechatUser.setSex(Sex.male.toString());
					} else {
						wechatUser.setSex(Sex.female.toString());
					}
					wechatUser.setCountry(country);
					wechatUser.setProvince(province);
					wechatUser.setCity(city);
					wechatUser.setOpenId(openId);
					wechatUser.setNick(nickname);
					wechatUser.setPicUrl(headimgurl);
					wechatUser.setLoginLastTime(new Date());
					wechatUser.setAttentionType(AttentionType.concerned.toString());
					System.out.println("用户" + nickname + "首次关注,openId:" + openId);
					wechatUserMapper.insert(wechatUser);
				}
				return wechatUser;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null; 
    }
	
	// 更新微信用户邀请人
	private void updateUserInviter(String eventKey, WechatUser wechatUser) {
		eventKey = eventKey.replace("qrscene_", "");
		// 如果存在自带的额外参数，说明是扫描邀请二维码关注的
		if (eventKey.indexOf("invite_") != -1) {
			eventKey = eventKey.substring(7);// 截取后面的部分
			WechatUser ue = wechatUserMapper.findByOpenId(eventKey);
			if (ue != null && StringUtils.isEmpty(wechatUser.getRefereeId())) {
				wechatUser.setRefereeId(ue.getOpenId());
				wechatUserMapper.updateByPrimaryKey(wechatUser);
			}
		}
	}

	// 订阅关注事件
	private String subscribeEvent(String accessToken, String eventKey,
			String fromUserName, String toUserName) {
		
		// 初始化用户
		WechatUser wechatUser = initUser(accessToken, fromUserName);
		
		// 更新用户的邀请人信息
		if (StringUtils.isNotEmpty(eventKey)) {
			updateUserInviter(eventKey, wechatUser);
		}
		
		// 关注回复
		Map<String, Object> replyMap = new HashMap<String, Object>();
		replyMap.put("type", ReplyType.attention.toString());
		List<WechatReply> replies = wechatReplyMapper.getListByConditions(replyMap);
		return replyMsg(fromUserName, toUserName, accessToken, replies);
	}
	
	// 取消订阅关注事件
	private String unsubscribeEvent(String fromUserName, String toUserName) {
		WechatUser wechatUser = wechatUserMapper.findByOpenId(fromUserName);
		if (wechatUser != null) {
			wechatUser.setAttentionType(AttentionType.unsubscribe.toString());
			System.out.println("用户" + wechatUser.getNick() + "取消关注,openId:"
					+ fromUserName);
			wechatUserMapper.updateByPrimaryKey(wechatUser);
		}
		return "success";
	}
	
	// 扫码事件
	private String scanEvent(String accessToken, String eventKey,
			String fromUserName, String toUserName) {
		
		// 初始化用户
		WechatUser wechatUser = initUser(accessToken, fromUserName);
		
		// 更新用户的邀请人信息
		if (StringUtils.isNotEmpty(eventKey)) {
			updateUserInviter(eventKey, wechatUser);
		}
		
		// 回复内容
		return responseMessageForText(fromUserName, toUserName, "你已经关注过本公众号");
	}
	
	// 自定义点击消息拉取事件
	private String clickEvent(String accessToken, String eventKey,
			String fromUserName, String toUserName) {
		
		// 初始化用户
		initUser(accessToken, fromUserName);
		
		// 菜单事件回复
		Map<String, Object> replyMap = new HashMap<String, Object>();
		replyMap.put("type", ReplyType.click.toString());
		replyMap.put("keyword", eventKey);
		List<WechatReply> replies = wechatReplyMapper.getListByConditions(replyMap);

		// 回复内容
		return replyMsg(fromUserName, toUserName, accessToken, replies);
	}
	
	// 自定义点击链接跳转事件
	private void viewEvent(String accessToken, String eventKey,
			String fromUserName, String toUserName) {
		
		// 初始化用户
		initUser(accessToken, fromUserName);
		
		// 根据openid可以进行一些业务处理
		// ...
	}
	
	// 微信公众号回复消息给用户
	private String replyMsg(String fromUserName, String toUserName,
			String accessToken, List<WechatReply> replies) {
		
		String respMessage = null;
		try {
			if(replies != null && replies.size() > 0){
				// 如果查询到匹配的关键字回复消息只有一条，则判断回复消息类型(图文，文本，图片)
				if(replies.size() == 1){
					WechatReply reply = replies.get(0);
					switch (reply.getContentType()) {
						case WechatReply.CONTENT_TYPE_GRAPHICS:
							respMessage = responseMessageForTextImage(fromUserName, toUserName,
									reply.getTitle(), reply.getContent(), reply.getImgUrl(), reply.getUrl());
							break;
						case WechatReply.CONTENT_TYPE_TEXT:
							respMessage = responseMessageForText(fromUserName, toUserName, reply.getContent());
							break;
						case WechatReply.CONTENT_TYPE_IMAGE:
							respMessage = responseMessageForImage(fromUserName, toUserName,
									reply.getImgUrl(), accessToken);
							break;
						default:
							respMessage = null;
							break;
					}
				}
				// 如果查询到匹配的的关键字回复消息大于一条，则说明是回复图文列表
				else {
					respMessage = responseMessageForTextImageList(fromUserName, toUserName, replies);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respMessage;
	}
	
	// 封装文本响应消息
	private String responseMessageForText(String fromUserName, String toUserName, String content) {
		TextMessage textMessage = new TextMessage();
		textMessage.setToUserName(fromUserName);
		textMessage.setFromUserName(toUserName);
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		textMessage.setFuncFlag(0);
		textMessage.setContent(content);
		String respMessage = MessageUtil.textMessageToXml(textMessage);
		return respMessage;
	}
	
	// 封装图片响应消息
	private String responseMessageForImage(
			String fromUserName, String toUserName, String imgUrl,
			String accessToken) throws Exception{
		String uploadPath = webConfig.getUploadPath() + imgUrl;
		
		// 获取图片存放的本地路径
		JSONObject jsonobj = WechatUploadFile.uploadFile("image", uploadPath, accessToken);
		// 将图片上传至微信服务器并返回mediaId
		String mediaId = jsonobj.getString("media_id");
		if (StringUtils.isNotEmpty(mediaId)) {
			ImageMessage img = new ImageMessage();
			img.setToUserName(fromUserName);
			img.setFromUserName(toUserName);
			img.setCreateTime(new Date().getTime());
			img.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_IMAGE);
			Image im = new Image();
			im.setMediaId(mediaId);
			img.setImage(im);
			String respMessage = MessageUtil.imageMessageToXml(img);
			return respMessage;
		}
		else return null;
	}
	
	// 封装图文响应消息
	private String responseMessageForTextImage(String fromUserName, String toUserName,
			String title, String content, String imgUrl, String url) {
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		newsMessage.setFuncFlag(0);
		List<Article> aList = new ArrayList<Article>();
		Article a = new Article();
		a.setTitle(title);
		a.setDescription(content);
		a.setPicUrl(webConfig.getSystemUrl() + webConfig.getViewPath() + imgUrl);
		if (StringUtils.isNotEmpty(url)) {
			a.setUrl(url);
		}
		aList.add(a);
		// 设置图文消息个数
		newsMessage.setArticleCount(aList.size());
		// 设置图文消息包含的图文集合
		newsMessage.setArticles(aList);
		// 将图文消息对象转换成xml字符串
		String respMessage = MessageUtil.newsMessageToXml(newsMessage);
		return respMessage;
	}
	
	// 封装图文列表响应消息
	private String responseMessageForTextImageList(String fromUserName, String toUserName,
			List<WechatReply> replies) {
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		newsMessage.setFuncFlag(0);
		List<Article> aList = new ArrayList<Article>();
		for (WechatReply reply : replies) {
			if (StringUtils.isNotEmpty(reply.getTitle()) &&
					StringUtils.isNotEmpty(reply.getImgUrl()) &&
					StringUtils.isNotEmpty(reply.getUrl())) {
				Article a = new Article();
				a.setTitle(reply.getTitle());
				a.setDescription(reply.getContent());
				a.setPicUrl(webConfig.getSystemUrl() + webConfig.getViewPath() + reply.getImgUrl());
				if (StringUtils.isNotEmpty(reply.getUrl())) {
					a.setUrl(reply.getUrl());
				}
				aList.add(a);
			}
		}
		
		// 设置图文消息个数
		newsMessage.setArticleCount(aList.size());
		// 设置图文消息包含的图文集合
		newsMessage.setArticles(aList);
		// 将图文消息对象转换成xml字符串
		String respMessage = MessageUtil.newsMessageToXml(newsMessage);
		return respMessage;
	}
}
