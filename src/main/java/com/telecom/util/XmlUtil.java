package com.telecom.util;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Map;

public class XmlUtil {

	/**
	 * DEMO返回结果接口XML解析
	 * @param xmlDoc xml
	 * @return Map
	 */
	public static Map<String, String> ecloudResult(String xmlDoc){
		Map<String, String> map = new HashMap<String, String>();
		try {
			Document doc = DocumentHelper.parseText(xmlDoc);
			Element root = doc.getRootElement();
			
			map.put("returnstatus", root.element("returnstatus").getText());
			map.put("message", root.element("message").getText());
			map.put("remainpoint", root.element("remainpoint").getText());
			map.put("taskID", root.element("taskID").getText());
			map.put("successCounts", root.element("successCounts").getText());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
}
