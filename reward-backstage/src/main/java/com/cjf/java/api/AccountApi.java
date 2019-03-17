package com.cjf.java.api;

public interface AccountApi {

	String BASEAPI = "/account";
	
	String TEST_METHOD = "/testMethod";
	/**
	 * 修改密码
	 */
	String UPDATE_ACCOUNT = "/updateAccount"; 
	/**
	 * 新增账号
	 */
	String ADD_ACCOUNT = "/addAccount"; 
	/**
	 * 删除账号
	 */
	String DELETE_ACCOUNT = "/deleteAccount"; 

	/**
	 * 分页获取账号信息
	 */
	String GET_ACCOUNTS = "/getAccounts"; 
}
