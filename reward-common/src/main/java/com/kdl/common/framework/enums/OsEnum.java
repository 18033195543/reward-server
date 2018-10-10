package com.kdl.common.framework.enums;

import com.kdl.common.util.jdkUtil.StringUtil;

public enum OsEnum {

    IOS(1,"IOS"),
    ANDROID(2,"ANDROID"),
    WINDOWS(3,"WINDOWS"),
    LINUX(4,"LINUX"),
    MAC(5,"MAC"),
    OTHER(6,"OTHER");

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
    private OsEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public  static  OsEnum getByValue(String value){
        if(StringUtil.isNotBlank(value)){
            for(OsEnum os : OsEnum.values()){
                if(os.getValue().equals(value)){
                    return os;
                }
            }
        }
        return  null;
    }
}
