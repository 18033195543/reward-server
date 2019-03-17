package com.cjf.java.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjf.java.api.dto.LoginDto;
import com.cjf.java.dao.LoginMapper;
import com.cjf.java.entity.AccountEntity;
import com.cjf.java.service.LoginService;
import com.cjf.java.utils.Md5Util;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginMapper loginMapper;
	
	@Override
	public AccountEntity login(LoginDto loginDto) {
		String password = loginDto.getPassword();
		//MD5加密
		String generatePassword = Md5Util.generatePassword(password);
		
		loginDto.setPassword(generatePassword);
		AccountEntity accountEntity = loginMapper.getAccountByAccount(loginDto);
		return accountEntity;
	}
	
	public static void main(String[] args) {
		String password = "123456";
		String generatePassword = Md5Util.generatePassword(password);
		System.out.println(generatePassword);
	}

}
