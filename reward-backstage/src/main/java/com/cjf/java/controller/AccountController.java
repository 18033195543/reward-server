package com.cjf.java.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cjf.java.api.AccountApi;
import com.cjf.java.api.dto.AccountDto;
import com.cjf.java.entity.AccountEntity;
import com.cjf.java.service.AccountService;
import com.kdl.common.framework.http.JSONResult;

@RestController
@RequestMapping(AccountApi.BASEAPI)
public class AccountController {

	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
	
	@Autowired
	private AccountService accountService;
	
	
	@RequestMapping(AccountApi.TEST_METHOD)
	public String testMethod() {
		return "ok";
	}
	
	/**
	 * 修改用户密码
	 * @param accountDto
	 * @param bindingResult
	 * @return
	 */
	@PostMapping(AccountApi.UPDATE_ACCOUNT)
	public JSONResult updateAccount(@Valid AccountDto accountDto,BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			logger.info("/updateAccount--传入参数:{}",bindingResult.getFieldError().getDefaultMessage());
			return JSONResult.fail(null, bindingResult.getFieldError().getDefaultMessage());
		}
		
		if(!accountDto.getPassword().equals(accountDto.getConfirmPassword())) {
			logger.info("新密码与确认密码必须一致！");
			return JSONResult.fail(null, "新密码与确认密码必须一致！");
		}
		
		String result = accountService.updateAccount(accountDto);
		if(result.equals("0000")) {
			return JSONResult.success(null, "修改成功!");
		}else {
			return JSONResult.fail(null, result);
		}
	}
	
	/**
	 * 新增账号信息
	 * @param accountDto
	 * @param bindingResult
	 * @return
	 */
	@PostMapping(AccountApi.ADD_ACCOUNT)
	public JSONResult addAccount(@Valid AccountDto accountDto,BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			logger.info("/updateAccount--传入参数:{}",bindingResult.getFieldError().getDefaultMessage());
			return JSONResult.fail(null, bindingResult.getFieldError().getDefaultMessage());
		}
		
		String accountName = accountDto.getAccountName();
		String password = accountDto.getPassword();
		
		// 此账号是否已存在
		AccountEntity account = accountService.getAccount(accountName, password);
		if(account != null) {
			logger.info("此账号已存在:{}",accountName);
			return JSONResult.fail("此账号已存在！");
		}
		accountService.addAccount(accountDto);
		
		return JSONResult.success("新增账号成功!");
	}
	
	@PostMapping(AccountApi.DELETE_ACCOUNT)
	public JSONResult deleteAccount(Integer id) {
		if(id == null) {
			logger.info("账号id不能为空！");
			JSONResult.fail("账号id为空!");
		}
		accountService.deleteAccountById(id);
		
		return JSONResult.success("删除成功!");
	}
	
	/**
	 * 分页查询账号信息
	 * @param page
	 * @param rows
	 * @return
	 */
	@GetMapping(AccountApi.ADD_ACCOUNT)
	public JSONResult getAccounts(Integer page, Integer rows) {
		List<AccountEntity> accounts = accountService.getAccounts(page, rows);
		return JSONResult.success(accounts, "查询成功!");
	}
	
}
