package com.telecom.pay.wechat.wap;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.*;

public class XmlUtil {
	
	private List<Map<String, Object>> elemList = new ArrayList<Map<String, Object>>();
	
	public List<Map<String, Object>> getList(final String strXml) {
		List<Map<String, Object>> it = null;
		try {
			Element root = getRootElement(strXml);
			getElementList(root);
			it = elemList;
		} catch (DocumentException docEx) {
			docEx.printStackTrace();
		}
		return it;
	}

	/**
	 * 获取根元素
	 * 
	 * @return
	 * @throws DocumentException
	 */
	private Element getRootElement(final String strXml) throws DocumentException {
		Document srcdoc = DocumentHelper.parseText(strXml);
		Element elem = srcdoc.getRootElement();
		return elem;
	}

	/**
	 * 递归遍历方法
	 * 
	 * @param element
	 */
	private void getElementList(Element element) {
		List<?> elements = element.elements();
		if (elements.size() == 0) {
			// 没有子元素
			String xpath = element.getPath();
			String key = element.getName();
			String value = element.getTextTrim();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("path", xpath);
			map.put("key", key);
			map.put("value", value);
			elemList.add(map);
		} else {
			// 有子元素
			for (Iterator<?> it = elements.iterator(); it.hasNext();) {
				Element elem = (Element) it.next();
				// 递归遍历
				getElementList(elem);
			}
		}
	}

}