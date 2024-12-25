package com.telecom.util.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
  
/**
 * 数据库连接
 *
 */
public class DataBaseHelper {  
  
    public Connection conn = null;  
    public PreparedStatement pst = null;  
  
    public DataBaseHelper(String sql, String url, String user, String password) {  
        try {  
            Class.forName("com.mysql.cj.jdbc.Driver");//指定连接类型  
            conn = DriverManager.getConnection(url, user, password);//获取连接  
            pst = conn.prepareStatement(sql);//准备执行语句  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    public void close() {  
        try {  
            this.conn.close();  
            this.pst.close();  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    }  
}  
