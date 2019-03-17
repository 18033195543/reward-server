package com.kdl.common.framework.http;

public class JSONResult {

    public final static Integer SUCCESS_CODE = 0;
    public final static Integer FAIL_CODE=1000;


    public static JSONResult success(Object data , String msg) {
        return genResult(SUCCESS_CODE, data, msg);
    }

    public static JSONResult success(Object data ) {
        return success(data, null);
    }
    
    public static JSONResult success(String msg) {
    	return success(null, msg);
    }

    public static JSONResult success() {
        return success(null);
    }

    public static JSONResult fail(Object data , String msg) {
        return genResult(FAIL_CODE, data, msg);
    }

    public static JSONResult fail(Object data) {
        return fail(data,null);
    }
    
    public static JSONResult fail(String msg) {
    	return fail(null,msg);
    }

    public static JSONResult fail() {
        return fail(null);
    }

    private static JSONResult genResult(Integer code,Object data, String msg) {
        if(code == null) {
            code = FAIL_CODE;
        }
        return new JSONResult(code, msg, data);
    }




    private Integer code;
    private String msg;
    private Object data;

    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }
    public long getTimestamp() {
        return System.currentTimeMillis();
    }


    private JSONResult() {};
    private JSONResult(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    
    public boolean ifSuccess() {
    	return this.getCode().intValue() == SUCCESS_CODE;
    }
}
