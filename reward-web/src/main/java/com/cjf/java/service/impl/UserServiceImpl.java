package com.cjf.java.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjf.java.api.dto.UserLoginDto;
import com.cjf.java.api.dto.UserRegisterDto;
import com.cjf.java.api.vo.UserAccountInfoVo;
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
	public String userRegister(UserRegisterDto userDto, HttpServletRequest request) {
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
		userEntity.setPassword(null);
		request.getSession().setAttribute("user", userEntity);
		return "0000";
	}

	@Override
	public UserEntity queryUserByNameAndPassword(UserLoginDto userLoginDto) {
		// 现在查询是否存在此用户
		return userMapper.queryUserByNameAndPassword(userLoginDto);
	}

	@Override
	public UserAccountInfoVo queryUserInfo(Integer userId) {
		//First query user and user account information.
		UserAccountInfoVo vo = userMapper.querUserInfoByUserId(userId);
		return vo;
	}

}
