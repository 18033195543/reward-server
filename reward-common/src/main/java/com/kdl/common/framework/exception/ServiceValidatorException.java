package com.kdl.common.framework.exception;

import com.kdl.common.util.jdkUtil.NDCUtils;

/**
 * 业务规则校验异常
 * @author Administrator
 *
 */
public class ServiceValidatorException extends FrameworkException {
	private static final long serialVersionUID = 1L;
	public static  final  Integer CODE_PREFIX = 9000;
	private int code;
	private String reqId;
	 
	@Override
	public Integer getCode() {
		return CODE_PREFIX + code;
	}
	@Override
	public String reqUid() {
		return this.reqId;
	}

	public ServiceValidatorException(int code,String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.code = code;
		this.reqId = NDCUtils.peek();
	}

	public ServiceValidatorException(int code,String message, Throwable cause) {
		super(message, cause);
		this.code = code;
		this.reqId = NDCUtils.peek();
	}

	public ServiceValidatorException(int code,String message) {
		super(message);
		this.code = code;
		this.reqId = NDCUtils.peek();
	}
	public ServiceValidatorException(String message) {
		super(message);
		this.reqId = NDCUtils.peek();
	}
}
