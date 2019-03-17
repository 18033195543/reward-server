package com.cjf.java.service.impl;

import java.util.List;

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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAccountById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AccountEntity getAccount(String name, String pwd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AccountEntity> getAccounts(int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AccountEntity> getAccounts(List<String> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AccountRoleEntity> getAccountRoles(int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AccountRoleEntity> getAccountRolesByAccountId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addAccountRoles(Integer accountId, Integer[] roleIds) {
		// TODO Auto-generated method stub
		
	}

}
