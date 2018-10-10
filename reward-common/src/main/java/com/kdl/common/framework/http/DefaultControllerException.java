package com.kdl.common.framework.http;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.kdl.common.framework.exception.ParamValidatorException;
import com.kdl.common.framework.exception.ServiceValidatorException;

import lombok.Getter;
import lombok.Setter;


public class DefaultControllerException {
	protected static final Logger logger = LoggerFactory.getLogger(ControllerAdvice.class);
	public static final String TEXT = "系统服务繁忙，请稍后再试";

	//TODO: 需要对各种错码进行分段分类
	
	/**
	 * 
	 * 全局异常捕捉处理
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(value = Exception.class)
	public ExcetpionResult errorHandler(Exception ex) {
		logger.error(ex.getMessage());
		return ExcetpionResult.build(JSONResult.FAIL_CODE+"", TEXT);
	}
	
	@ExceptionHandler(BindException.class)
	public ExcetpionResult bindException(BindException ex) {
		logger.error(ex.getMessage());
		StringBuffer sb = new StringBuffer();
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			sb.append("[");
			if(error instanceof FieldError) {
				FieldError fe = (FieldError)error;
				sb.append(fe.getField()).append(" ");
			}
			sb.append(error.getDefaultMessage()).append("] ");
		}
		return ExcetpionResult.build(JSONResult.FAIL_CODE+"", sb.toString());
	}

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ExcetpionResult validErrorHandler(MethodArgumentNotValidException e) {
		logger.error(e.getMessage());
		StringBuffer sb = new StringBuffer();
		for (ObjectError error : e.getBindingResult().getAllErrors()) {
			sb.append("[").append(error.getDefaultMessage()).append("] ");
		}
		return ExcetpionResult.build(JSONResult.FAIL_CODE+"", sb.toString());
	}

	@ExceptionHandler(value = ParamValidatorException.class)
	public ExcetpionResult myParamHandler(ParamValidatorException ex) {
		logger.error(ex.getMessage());
		return ExcetpionResult.build(ex.getCode()+"", ex.getMessage());
	}

	@ExceptionHandler(value = ServiceValidatorException.class)
	public ExcetpionResult myServiceExceptionHandler(ServiceValidatorException ex) {
		logger.error(ex.getMessage());
		return ExcetpionResult.build(ex.getCode()+"", ex.getMessage());
	}
	
	protected static class ExcetpionResult{
		@Getter
		@Setter
		private String code;
		@Getter
		private Long timestamp;
		@Getter
		@Setter
		private String msg;
		@Getter
		@Setter
		private Object data;
		
		public static ExcetpionResult build(String code,String msg) {
			return new ExcetpionResult(code, msg);
		}
		private ExcetpionResult(String code,String msg) {
			this.code = code;
			this.msg = msg;
			this.timestamp = System.currentTimeMillis();
		}
	}
}
