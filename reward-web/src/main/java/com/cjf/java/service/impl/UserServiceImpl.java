package com.cjf.java.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjf.java.api.dto.UserRegisterDto;
import com.cjf.java.dao.UserMapper;
import com.cjf.java.entity.UserEntity;
import com.cjf.java.enums.UserStatusEnum;
import com.cjf.java.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public String userRegister(UserRegisterDto userDto) {
		// 现在查询是否存在此用户
		int existsPhone = userMapper.queryRegisterPhoneNumByPhoneNum(userDto.getPhoneNum());
		// 判断
		if(existsPhone != 0 ) {
			log.info("此手机号已被注册,phoneNum:{}",userDto.getPhoneNum());
			return "此手机号已被注册";
		}
		// copy userDto
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(userDto, userEntity);
		userEntity.setInputTime(System.currentTimeMillis());
		userEntity.setStatus(UserStatusEnum.EFFECTIVE.getCode());
		// 插入用户信息
		int result = userMapper.addUser(userEntity);
		
		if(result != 1) {
			log.info("用户注册失败，插入数据失败!");
			return "用户注册失败！";
		}
		log.info("用户注册成功!");
		return "0000";
	}

}
