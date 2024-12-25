package com.telecom.official.pojo;

import java.io.Serializable;

/**
 * 微信第三方开放平台预授权码凭证
 * 
 */
public class PreAuthCode implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 获取到的凭证
	private String preAuthcode;
	// 凭证有效时间，单位：秒
	private int expiresIn;

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getPreAuthcode() {
		return preAuthcode;
	}

	public void setPreAuthcode(String preAuthcode) {
		this.preAuthcode = preAuthcode;
	}
}
