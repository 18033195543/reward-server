package com.kdl.common.util;


import com.kdl.common.framework.exception.ParamValidatorException;
import com.kdl.common.framework.exception.ServiceValidatorException;

public class AssertUtil {
	public static final int PARAM_VALI_CODE = 501;
	public static final int BIZ_VALI_CODE = 502;
	
	public static void assertParamValidatorException(boolean assertValue,String message) {
		assertParamValidatorException(assertValue, PARAM_VALI_CODE,message);
	}
	
	public static void assertParamValidatorException(boolean assertValue,Integer code,String message) {
		if(assertValue) {
			throw new ParamValidatorException(code, message);
		}
	}
	
	public static void assertServiceExcetpion(boolean assertValue,String message) {
		assertServiceExcetpion(assertValue,BIZ_VALI_CODE, message);
	}
	public static void assertServiceExcetpion(boolean assertValue,Integer code,String message) {
		if(assertValue) {
			throw new ServiceValidatorException(code, message);
		}
	}

}
