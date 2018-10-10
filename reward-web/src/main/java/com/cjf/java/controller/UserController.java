package com.cjf.java.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cjf.java.api.UserApi;
import com.cjf.java.api.dto.UserLoginDto;
import com.cjf.java.api.dto.UserRegisterDto;
import com.cjf.java.service.UserService;
import com.kdl.common.framework.http.JSONResult;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(UserApi.BASEAPI)
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;
	
	/**
	 * 用户注册接口
	 * @param userDto
	 * @return
	 */
	@RequestMapping(value=UserApi.USER_REGISTER, method = RequestMethod.POST)
	public JSONResult userRegister(@Valid @RequestBody UserRegisterDto userDto,BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			log.info("/user/userRegister--传入参数:{}",bindingResult.getFieldError().getDefaultMessage());
			return JSONResult.fail(null, bindingResult.getFieldError().getDefaultMessage());
		}
		String result = userService.userRegister(userDto);
		if(result.equals("0000")) {
			log.info("注册成功!phoneNum:{}",userDto.getPhoneNum());
			return JSONResult.success(null, "注册成功!");
		}else {
			log.info("注册失败!结果:{}",result);
			return JSONResult.fail(null, result);
		}
	}
	
	@RequestMapping(value=UserApi.USER_LOGIN, method=RequestMethod.POST)
	public JSONResult userLogin(@RequestBody @Valid UserLoginDto userLoginDto,BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			log.info("/user/userLogin--传入参数:{}",bindingResult.getFieldError().getDefaultMessage());
			return JSONResult.fail(null, bindingResult.getFieldError().getDefaultMessage());
		}
		
		
		
		return null;
		
	}
}
