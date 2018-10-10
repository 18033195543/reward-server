package com.cjf.java.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.cjf.java.api.LoginApi;
import com.cjf.java.api.dto.LoginDto;
import com.cjf.java.entity.AccountEntity;
import com.cjf.java.service.LoginService;
import com.kdl.common.framework.http.JSONResult;

@RestController
@RequestMapping(LoginApi.BASEAPI)
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private LoginService loginService;
	
	@RequestMapping(value=LoginApi.LOGIN,method=RequestMethod.POST)
	public JSONResult login(@RequestBody @Valid LoginDto loginDto, BindingResult bindingResult , HttpServletRequest request) {
		
		if(bindingResult.hasErrors()) {
			logger.info("/login--传入参数为,loginDto:{}", bindingResult.getFieldError().getDefaultMessage());
			return JSONResult.fail(null, bindingResult.getFieldError().getDefaultMessage());
		}
		
		AccountEntity accountEntity = loginService.login(loginDto);
		
		if(accountEntity == null) {
			logger.info("用户名密码错误！accountName:{}",loginDto.getAccountName());
			return JSONResult.fail(null, "用户名密码错误！");
		}
		
		request.getSession().setAttribute("account", accountEntity);
		return JSONResult.success(accountEntity,"登录成功！");
	}
	
	@RequestMapping(value =LoginApi.LOGINOUT)
	public JSONResult loginOut(HttpServletRequest request) {
		AccountEntity account = (AccountEntity) request.getSession().getAttribute("account");
		if(account != null) {
			logger.info("退出成功！");
			request.getSession().invalidate();
		}
		return JSONResult.success(null, "退出成功！");
		
	}
	
}
