package com.cjf.java.api;

public interface UserApi {

	/**
	 * Controller base api;
	 */
	String BASEAPI = "/user";
	
	/**
	 * User Register account;
	 */
	String USER_REGISTER = "/userRegister";
	
	/**
	 * Cser login system;
	 */
	String USER_LOGIN = "/userLogin";
	
	/**
	 * My page use , query user and account information;
	 */
	String QUERY_USER_INFO = "/queryUserInfo/{userId}";
}
