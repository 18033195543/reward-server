package com.kdl.data.exception;

public class DaoValidatorException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private final Integer code = 2000;
	private String message;
	
	public DaoValidatorException(String msg) {
		this.message = msg;
	}
	
	


	public String getMessage() {
		return message;
	}

	public Integer getCode() {
		return code;
	}
}
