package com.cjf.java.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cjf.java.api.UserApi;
import com.cjf.java.api.dto.UserLoginDto;
import com.cjf.java.api.dto.UserRegisterDto;
import com.cjf.java.api.vo.UserAccountInfoVo;
import com.cjf.java.entity.UserEntity;
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
	public JSONResult userRegister(@Valid @RequestBody UserRegisterDto userDto,BindingResult bindingResult, HttpServletRequest request) {
		if(bindingResult.hasErrors()) {
			log.info("/user/userRegister--传入参数:{}",bindingResult.getFieldError().getDefaultMessage());
			return JSONResult.fail(null, bindingResult.getFieldError().getDefaultMessage());
		}
		String result = userService.userRegister(userDto, request);
		if(result.equals("0000")) {
			log.info("注册成功!phoneNum:{}",userDto.getPhoneNum());
			return JSONResult.success(null, "注册成功!");
		}else {
			log.info("注册失败!结果:{}",result);
			return JSONResult.fail(null, result);
		}
	}
	
	/**
	 * 用户登录接口
	 * @param userLoginDto
	 * @param bindingResult
	 * @param request
	 * @return
	 */
	@RequestMapping(value=UserApi.USER_LOGIN, method=RequestMethod.POST)
	public JSONResult userLogin(@RequestBody @Valid UserLoginDto userLoginDto,BindingResult bindingResult, HttpServletRequest request) {
		if(bindingResult.hasErrors()) {
			log.info("/user/userLogin--传入参数:{}",bindingResult.getFieldError().getDefaultMessage());
			return JSONResult.fail(null, bindingResult.getFieldError().getDefaultMessage());
		}
		
		UserEntity userEntity = userService.queryUserByNameAndPassword(userLoginDto);
		
		if(userEntity == null) {
			log.info("账号或密码错误！,phone:{}",userLoginDto.getPhoneNum());
			return JSONResult.fail(null, "账号或密码错误！");
		}
		
		log.info("登录成功！phone:{}",userLoginDto.getPhoneNum());
		request.getSession().setAttribute("user", userEntity);
		
		return JSONResult.success(userEntity, "登录成功!");
	}
	
	/**
	 * My page api use,query user and account information;
	 * @return
	 */
	@RequestMapping(value=UserApi.QUERY_USER_INFO,method=RequestMethod.GET)
	public JSONResult queryUserInfo(HttpServletRequest request, @PathVariable Integer userId) {
		log.info("/queryUserInfo/{userId}--传入参数:{}",userId);
		UserAccountInfoVo vo = userService.queryUserInfo(userId);
		return JSONResult.success(vo, "查询成功！");
	}
}
