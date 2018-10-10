package com.cjf.java.enums;

public enum UserStatusEnum {

	EFFECTIVE(1,"有效"),
	INVALID(0,"无效");
	
	private int code;
	private String msg;
	
	UserStatusEnum(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
