package com.kdl.common.framework.exception;

import com.kdl.common.util.jdkUtil.NDCUtils;

public class ParamValidatorException extends FrameworkException {
    private static final long serialVersionUID = 1L;
    public static  final  Integer CODE_PREFIX = 3000;
    private String reqId;
    private int code;

    @Override
    public Integer getCode() {
        return CODE_PREFIX + code;
    }

    @Override
	public String reqUid() {
		return this.reqId;
	}
    
    public ParamValidatorException(int code,String message, Throwable cause, boolean enableSuppression,
                                   boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
        this.reqId = NDCUtils.peek();
    }

    public ParamValidatorException(int code,String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.reqId = NDCUtils.peek();
    }

    public ParamValidatorException(int code,String message) {
        super(message);
        this.code = code;
        this.reqId = NDCUtils.peek();
    }
    
    public ParamValidatorException(String message) {
        super(message);
        this.reqId = NDCUtils.peek();
    }
}
