package com.telecom.bean;

import java.io.Serializable;

/**
 * 通用结果CommonResult
 * msg : 结果描述
 * flag : 结果标识    true-成功、false-失败
 *
 */
public class CommonResult implements Serializable {

	private static final long serialVersionUID = 7465342926652146871L;
	
	private String msg;
	private Boolean flag;
	
	public CommonResult() {}
	
	public CommonResult(Boolean flag, String msg) {
		this.msg = msg;
		this.flag = flag;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

}
