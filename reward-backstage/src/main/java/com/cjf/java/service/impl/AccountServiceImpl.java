package com.cjf.java.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjf.java.api.dto.AccountDto;
import com.cjf.java.dao.AccountMapper;
import com.cjf.java.dao.AccountRoleMapper;
import com.cjf.java.entity.AccountEntity;
import com.cjf.java.entity.AccountRoleEntity;
import com.cjf.java.service.AccountService;
import com.cjf.java.utils.Md5Util;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountMapper accountMapper;
	
	@Autowired
	private AccountRoleMapper accountRoleMapper;
	
	@Override
	public String updateAccount(AccountDto accountDto) {
		
		String oldPassword = Md5Util.generatePassword(accountDto.getOldPassword());
		accountDto.setOldPassword(oldPassword);
		// 根据就密码获取用户信息
		AccountEntity accountEntity = accountMapper.getAccountByOldPasswrod(accountDto);
		if(accountEntity == null) {
			log.info("旧密码输入有误!");
			return "旧密码输入有误!";
		}
		
		String password = Md5Util.generatePassword(accountDto.getPassword());
		// 判断是否和旧密码相同
		if(accountEntity.getPassword().equals(password)) {
			log.info("新密码和旧密码不能相同,请正确填写新密码!");
			return "新密码和旧密码不能相同,请正确填写新密码!";
		}
		
		AccountEntity accountEntityNew = new AccountEntity();
		BeanUtils.copyProperties(accountDto, accountEntityNew);
		accountEntityNew.setPassword(password);
		accountEntityNew.setUpdateTime(System.currentTimeMillis());
		int result = accountMapper.updateAccount(accountEntityNew);
		if(result == 1) {
			return "0000";
		}else {
			return "修改失败!";
		}
	}

	@Override
	public void addAccount(AccountDto accountDto) {
		AccountEntity accountEntity = new AccountEntity();
		BeanUtils.copyProperties(accountDto, accountEntity);
		String generatePassword = Md5Util.generatePassword(accountDto.getPassword());
		accountEntity.setPassword(generatePassword);
		int add = accountMapper.add(accountEntity);
	}

	@Override
	public void deleteAccountById(Integer id) {
		accountMapper.delete(id);
	}

	@Override
	public AccountEntity getAccount(String name, String pwd) {
		return accountMapper.getAccount(name, pwd);
	}

	@Override
	public List<AccountEntity> getAccounts(int offset, int rows) {
		Map<String, Object> map = new HashMap<String, Object>(2);
		map.put("offset", offset);
		map.put("rows", rows);
		return accountMapper.getAccounts(map);
	}

	@Override
	public List<AccountEntity> getAccounts(List<Integer> ids) {
		return accountMapper.getAccountsByIds(ids);
	}

	@Override
	public List<AccountRoleEntity> getAccountRoles(int offset, int rows) {
		Map<String, Object> map = new HashMap<String, Object>(2);
		map.put("offset", offset);
		map.put("rows", rows);
		return accountRoleMapper.getAccountRoles(map);
	}

	@Override
	public List<AccountRoleEntity> getAccountRolesByAccountId(Integer id) {
		return accountRoleMapper.getAccountRolesByAccountId(id);
	}

	@Override
	public void addAccountRoles(Integer accountId, Integer[] roleIds) {
		
		Long insertTime = System.currentTimeMillis();
		
		List<AccountRoleEntity> accountRoleList = new ArrayList<>();
		for (Integer roleId : roleIds) {
			AccountRoleEntity accountRoleEntity = new AccountRoleEntity();
			accountRoleEntity.setAccountId(accountId);
			accountRoleEntity.setRoleId(roleId);
			accountRoleEntity.setInsertTime(insertTime);
			accountRoleList.add(accountRoleEntity);
		}
		accountRoleMapper.addAccountRoles(accountRoleList);
	}
}
