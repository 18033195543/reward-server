package com.kdl.common.framework.enums;

public enum AccountStateEnum {
	/**
	 * 待激活
	 * 正常
	 * 异常锁定
	 * 删除
	 */
	UNACTIVATED(0),
	NOMAL(1),
	LOCKED(2),
	DELETED(3);
	
	private int code;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	private AccountStateEnum(int code) {
		this.code = code;
	}
	
}
