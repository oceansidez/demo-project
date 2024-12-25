package com.telecom.util;

import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * Yml解析工具类
 *
 */
@SuppressWarnings("unchecked")
public class YmlUtil {
	
	private static Map<String, String> result = new HashMap<>();

	/**
	 * 根据文件名获取yml的文件内容
	 * 
	 * @return
	 */
	public static Map<String, String> getYmlByFileName(File file) {
		result = new HashMap<>();
		try {
			Yaml props = new Yaml();
			Object obj = props.loadAs(new FileInputStream(file), Map.class);
			Map<String, Object> param = (Map<String, Object>) obj;
			for (Map.Entry<String, Object> entry : param.entrySet()) {
				String key = entry.getKey();
				Object val = entry.getValue();
	
				if (val instanceof Map) {
					forEachYaml(key, (Map<String, Object>) val);
				} else {
					result.put(key, val.toString());
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 遍历yml文件，获取map集合
	 * 
	 * @param key_str
	 * @param obj
	 * @return
	 */
	public static Map<String, String> forEachYaml(String key_str,
			Map<String, Object> obj) {
		for (Map.Entry<String, Object> entry : obj.entrySet()) {
			String key = entry.getKey();
			Object val = entry.getValue();
			String str_new = "";
			if (StringUtils.isNotEmpty(key_str)) {
				str_new = key_str + "." + key;
			} else {
				str_new = key;
			}
			if (val instanceof Map) {
				forEachYaml(str_new, (Map<String, Object>) val);
			} else {
				result.put(str_new, val.toString());
			}
		}
		return result;
	}

	/**
	 * 根据key获取值
	 * 
	 * @param key
	 * @return
	 */
	public static String getValue(String key) {
		Map<String, String> map = getYmlByFileName(null);
		if (map == null)
			return null;
		return map.get(key);
	}
}
