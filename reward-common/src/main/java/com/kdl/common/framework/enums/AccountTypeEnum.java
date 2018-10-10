package com.kdl.common.framework.enums;

public enum AccountTypeEnum {
    TELE(1),
    EMAIL(2),
    SINA(3),
    WECHAT(4),
    QQ(5);

    private  int code;
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    AccountTypeEnum(int code) {
        this.code = code;
    }

}
