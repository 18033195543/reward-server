package com.cjf.java.service;

import java.util.List;

import com.cjf.java.api.dto.AccountDto;
import com.cjf.java.entity.AccountEntity;
import com.cjf.java.entity.AccountRoleEntity;

public interface AccountService {

	/**
	 * 保存账号信息
	 * 
	 * @param accountDto
	 */
	void addAccount(AccountDto accountDto);

	/**
	 * 更新账号信息
	 * 
	 * @param accountDto
	 * @return
	 */
	String updateAccount(AccountDto accountDto);

	/**
	 * 根据id删除账号信息
	 * 
	 * @param id
	 */
	void deleteAccountById(Integer id);

	/**
	 * 根据用户名密码查询用户，用于登录
	 * 
	 * @param name
	 * @param pwd
	 * @return
	 */
	AccountEntity getAccount(String name, String pwd);

	/**
	 * 分页查询用户信息
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	List<AccountEntity> getAccounts(int page, int size);

	/**
	 * 根据id集合查询用户信息
	 * @param page
	 * @param size
	 * @return
	 */
	List<AccountEntity> getAccounts(List<String> ids);
	
	/**
	 * 分页查询用户角色对应关系
	 * @param page
	 * @param size
	 * @return
	 */
	List<AccountRoleEntity> getAccountRoles(int page, int size);
	
	/**
	 * 根据用户id查询用户对应角色关系
	 * @param id
	 * @return
	 */
	List<AccountRoleEntity> getAccountRolesByAccountId(Integer id);
	
	/**
	 * 保存用户角色对应关系
	 * @param accountId
	 * @param roleId
	 */
	void addAccountRoles(Integer accountId, Integer[] roleIds);
	
}
