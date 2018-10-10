package com.kdl.common.framework.enums;

public enum  UserTypeEnum {
    /**
     * 游客
     */
    GUEST(0),

    /**
     * App注册用户
     */
    APP_USER(1),
    
    /**
     * OPERATE   渠道商
     */
    OPERATE_USER(2),

    /**
     * 运营用户（公司内部管理专用）
     */
    ADMIN_USER(10);

    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    UserTypeEnum(Integer code) {
        this.code = code;
    }
}
