package com.kdl.common.framework.enums;

public enum SystemTypeEnum {

    DS_MONEY(1,"ds");

    private int code;
    private String aliase;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getAliase() {
		return aliase;
	}

	public void setAliase(String aliase) {
		this.aliase = aliase;
	}

	SystemTypeEnum(int code,String value) {
        this.code = code;
    }
}
