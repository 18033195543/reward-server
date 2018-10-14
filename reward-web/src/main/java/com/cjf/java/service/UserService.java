package com.cjf.java.service;

import javax.servlet.http.HttpServletRequest;

import com.cjf.java.api.dto.UserLoginDto;
import com.cjf.java.api.dto.UserRegisterDto;
import com.cjf.java.api.vo.UserAccountInfoVo;
import com.cjf.java.entity.UserEntity;

public interface UserService {

	String userRegister(UserRegisterDto userDto, HttpServletRequest request);

	UserEntity queryUserByNameAndPassword(UserLoginDto userLoginDto);

	UserAccountInfoVo queryUserInfo(Integer userId);

}
