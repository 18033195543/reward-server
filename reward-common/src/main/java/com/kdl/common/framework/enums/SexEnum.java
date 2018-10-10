package com.kdl.common.framework.enums;

import com.kdl.common.util.jdkUtil.StringUtil;

public enum  SexEnum {
    SECRET(0,"保密"),
    MALE(1,"男"),
    FEMALE(2,"女");

    private int code;
    private String value;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    SexEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }
    
    public  static  SexEnum getByValue(String value){
        if(StringUtil.isNotBlank(value)){
            for(SexEnum os : SexEnum.values()){
                if(os.getValue().equals(value)){
                    return os;
                }
            }
        }
        return  null;
    }
}
