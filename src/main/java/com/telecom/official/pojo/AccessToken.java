package com.telecom.official.pojo;

import java.io.Serializable;

/** 
 * 微信通用接口凭证 
 *  
 */  
public class AccessToken implements Serializable{  
	
	private static final long serialVersionUID = 1L;
	
	// 获取到的凭证  
    private String token;  
    // 凭证有效时间，单位：秒  
    private int expiresIn;  
  
    public String getToken() {  
        return token;  
    }  
  
    public void setToken(String token) {  
        this.token = token;  
    }  
  
    public int getExpiresIn() {  
        return expiresIn;  
    }  
  
    public void setExpiresIn(int expiresIn) {  
        this.expiresIn = expiresIn;  
    }  
}  
