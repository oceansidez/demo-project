package com.telecom.bean;

public class ValidateResult {

	private Boolean flag;
	private String message;

	public ValidateResult(Boolean flag, String message) {
		this.flag = flag;
		this.message = message;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
