package com.telecom.util.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取数据库所有表
 * 
 * @author Administrator
 *
 */
public class TableHelper {

	private static String sql = null;
	private static DataBaseHelper db = null;
	private static ResultSet ret = null;

	public static List<String> table(String url, String name, String password) {
		sql = "show tables";// SQL语句
		db = new DataBaseHelper(sql, url, name, password);// 创建DBHelper对象

		try {
			ret = db.pst.executeQuery();// 执行语句，得到结果集
			List<String> list = new ArrayList<String>();
			while (ret.next()) {
				String table_name = ret.getString(1);
				list.add(table_name);
			}// 显示数据
			ret.close();
			db.close();// 关闭连接
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static List<Map<String, String>> getField(String tableName, String url, String name, String password) {
		
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			sql = "show full columns from " + tableName;// SQL语句
			db = new DataBaseHelper(sql, url, name, password);// 创建DBHelper对象
			ret = db.pst.executeQuery();// 执行语句，得到结果集

			while (ret.next()) {
				String field = ret.getString(1);
				String type = ret.getString(2);
				String collation = ret.getString(3);
				String nullable = ret.getString(4);
				String key = ret.getString(5);
				String defaultValue = ret.getString(6);
				String extra = ret.getString(7);
				String privilenges = ret.getString(8);
				String comment = ret.getString(9);

				Map<String, String> map = new HashMap<String, String>();
				map.put("field", field);
				map.put("type", type);
				map.put("nullable", nullable);
				map.put("key", key);
				map.put("defaultValue", defaultValue);
				map.put("extra", extra);
				map.put("collation", collation);
				map.put("privilenges", privilenges);
				map.put("comment", comment);

				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
