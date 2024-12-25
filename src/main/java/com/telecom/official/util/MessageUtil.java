package com.telecom.official.util;

import com.telecom.official.crypt.AesException;
import com.telecom.official.crypt.WXBizMsgCrypt;
import com.telecom.official.response.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 消息工具类
 * 
 */
public class MessageUtil {
	/**
	 * 返回消息类型：文本
	 */
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 返回消息类型：音乐
	 */
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

	/**
	 * 返回消息类型：图文
	 */
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";

	/**
	 * 请求消息类型：文本
	 */
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 请求消息类型：多客服
	 */
	public static final String REQ_MESSAGE_TYPE_SERVICE = "transfer_customer_service";

	/**
	 * 请求消息类型：图片
	 */
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

	/**
	 * 请求消息类型：链接
	 */
	public static final String REQ_MESSAGE_TYPE_LINK = "link";

	/**
	 * 请求消息类型：地理位置
	 */
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

	/**
	 * 请求消息类型：音频
	 */
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";

	/**
	 * 请求消息类型：推送
	 */
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";

	/**
	 * 事件类型：subscribe(订阅)
	 */
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

	/**
	 * 事件类型：unsubscribe(取消订阅)
	 */
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

	/**
	 * 事件类型：scan(扫码)
	 */
	public static final String EVENT_TYPE_SCAN = "SCAN";

	/**
	 * 事件类型：click(自定义菜单点击消息拉取事件)
	 */
	public static final String EVENT_TYPE_CLICK = "CLICK";

	/**
	 * 事件类型：view(自定义菜单点击链接跳转事件)
	 */
	public static final String EVENT_TYPE_VIEW = "VIEW";
	
	/**
	 * 事件类型：TEMPLATESENDJOBFINISH(模板消息发送结果)
	 */
	public static final String EVENT_TYPE_TEMPLATESENDJOBFINISH = "TEMPLATESENDJOBFINISH";

	/**
	 * 消息解密解析-解密微信发来的请求（XML）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> decryptedParse(
			HttpServletRequest request, String token, String encodingAesKey,
			String appId) throws IOException, DocumentException, AesException {
		Date beginTime = new Date();
		SimpleDateFormat bartDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		System.out.println("================开始解密"
				+ bartDateFormat.format(beginTime) + "================");
		// 将解密解析的结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();
		String nonce = request.getParameter("nonce");
		String timestamp = request.getParameter("timestamp");
		String msgSignature = request.getParameter("msg_signature");
		System.out.print("nonce=" + nonce + "; timestamp=" + timestamp
				+ "; msgSignature=" + msgSignature);
		StringBuilder sb = new StringBuilder();
		BufferedReader in = request.getReader();
		String line;
		while ((line = in.readLine()) != null) {
			sb.append(line);
		}
		String xml = sb.toString();
		System.out.println("===第三方平台解密---原始 Xml=" + xml);
		WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
		System.out.println("===解密 WXBizMsgCrypt new 成功===");
		String xml1 = pc.decryptMsg(msgSignature, timestamp, nonce, xml);
		System.out.println("===第三方平台解密---解密后 Xml=" + xml1);
		Document document = DocumentHelper.parseText(xml1);
		Element root = document.getRootElement();
		List<Element> elementList = root.elements();
		System.out.println("解密elementList.size()=" + elementList.size());
		// 遍历所有子节点
		for (Element e : elementList) {
			map.put(e.getName(), e.getText());
			System.out.println(e.getName() + "= " + e.getText());
		}
		System.out.println("================解密结束================");
		return map;
	}

	/**
	 * 消息加密-加密给用户的信息（XML）
	 * 
	 * @param request
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws Exception
	 */
	public static String encryptionParse(String replyMsg, String token,
			String encodingAesKey, String appId) throws IOException,
			DocumentException, AesException, ParserConfigurationException,
			SAXException {
		Date beginTime = new Date();
		SimpleDateFormat bartDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		System.out.println("================开始加密"
				+ bartDateFormat.format(beginTime) + "================");
		System.out.println("===第三方平台加密---原始 replyMsg=" + replyMsg);
		String nonce = UUID.randomUUID().toString(); // 随机串
		String timestamp = System.currentTimeMillis() + ""; // 时间戳
		System.out.print("nonce=" + nonce + "; timestamp=" + timestamp);
		WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
		System.out.println("===加密 WXBizMsgCrypt new 成功===");
		String mingwen = pc.encryptMsg(replyMsg, timestamp, nonce);
		System.out.println("===第三方平台加密---加密后 replyMsg=" + mingwen);

		// DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		// DocumentBuilder db = dbf.newDocumentBuilder();
		// StringReader sr = new StringReader(mingwen);
		// InputSource is = new InputSource(sr);
		// org.w3c.dom.Document document = db.parse(is);
		//
		// org.w3c.dom.Element root = document.getDocumentElement();
		// NodeList nodelist1 = root.getElementsByTagName("Encrypt");
		//
		// String encrypt = nodelist1.item(0).getTextContent();
		// System.out.println("===第三方平台加密--- encrypt=" + encrypt);
		//
		// String format =
		// "<xml><ToUserName><![CDATA[toUser]]></ToUserName><Encrypt><![CDATA[%1$s]]></Encrypt></xml>";
		// String fromXML = String.format(format, encrypt);
		// System.out.println("===第三方平台加密---加密后返回 fromXML=" + fromXML);
		//
		System.out.println("================加密结束================");
		return mingwen;
	}

	/**
	 * 解析微信发来的请求（XML）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(HttpServletRequest request)
			throws Exception {
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();

		// 从request中取得输入流
		InputStream inputStream = request.getInputStream();
		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		List<Element> elementList = root.elements();

		// 遍历所有子节点
		for (Element e : elementList)
			map.put(e.getName(), e.getText());

		// 释放资源
		inputStream.close();
		inputStream = null;

		return map;
	}

	/**
	 * 文本消息对象转换成xml
	 * 
	 * @param textMessage
	 *            文本消息对象
	 * @return xml
	 */
	public static String textMessageToXml(TextMessage textMessage) {
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}

	/**
	 * 音乐消息对象转换成xml
	 * 
	 * @param musicMessage
	 *            音乐消息对象
	 * @return xml
	 */
	public static String musicMessageToXml(MusicMessage musicMessage) {
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}

	/**
	 * 图片消息对象转换成xml
	 * 
	 * @param imageMessage
	 *            音乐消息对象
	 * @return xml
	 */
	public static String imageMessageToXml(ImageMessage imageMessage) {
		xstream.alias("xml", imageMessage.getClass());
		return xstream.toXML(imageMessage);
	}

	/**
	 * 图文消息对象转换成xml
	 * 
	 * @param newsMessage
	 *            图文消息对象
	 * @return xml
	 */
	public static String newsMessageToXml(NewsMessage newsMessage) {
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new Article().getClass());
		return xstream.toXML(newsMessage);
	}

	/**
	 * 多客服消息对象转换成xml
	 * 
	 * @param imageMessage
	 *            音乐消息对象
	 * @return xml
	 */
	public static String serviceMessageToXml(BaseMessage baseMessage) {
		xstream.alias("xml", baseMessage.getClass());
		return xstream.toXML(baseMessage);
	}

	/**
	 * 扩展xstream，使其支持CDATA块
	 * 
	 * @date 2013-05-19
	 */
	private static XStream xstream = new XStream(new XppDriver() {
		@Override
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				// 对所有xml节点的转换都增加CDATA标记
				boolean cdata = true;

				@SuppressWarnings("rawtypes")
				@Override
				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
				}

				@Override
				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});

	/**
	 * 检测是否有emoji字符
	 * 
	 * @param source
	 * @return 一旦含有就抛出
	 */
	public static boolean containsEmoji(String source) {
		if (StringUtils.isBlank(source)) {
			return false;
		}
		int len = source.length();
		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);
			if (isEmojiCharacter(codePoint)) {
				// do nothing，判断到了这里表明，确认有表情字符
				return true;
			}
		}
		return false;
	}

	private static boolean isEmojiCharacter(char codePoint) {
		return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
				|| (codePoint == 0xD)
				|| ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
				|| ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
				|| ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
	}

	/**
	 * 过滤emoji 或者 其他非文字类型的字符
	 * 
	 * @param source
	 * @return
	 */
	public static String filterEmoji(String source) {
		source = " " + source;
		if (!containsEmoji(source)) {
			return source;// 如果不包含，直接返回
		}
		// 到这里铁定包含
		StringBuilder buf = null;
		int len = source.length();
		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);
			if (isEmojiCharacter(codePoint)) {
				if (buf == null) {
					buf = new StringBuilder(source.length());
				}
				buf.append(codePoint);
			} else {
			}
		}

		if (buf == null) {
			return source.trim();// 如果没有找到 emoji表情，则返回源字符串
		} else {
			if (buf.length() == len) {// 这里的意义在于尽可能少的toString，因为会重新生成字符串
				buf = null;
				return source.trim();
			} else {
				return buf.toString().trim();
			}
		}

	}

}
