package com.cjf.java.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cjf.java.api.LoginApi;
import com.cjf.java.api.dto.Accordion;
import com.cjf.java.api.dto.LoginDto;
import com.cjf.java.context.LoginAccountCache;
import com.cjf.java.context.NativeCache;
import com.cjf.java.entity.AccountEntity;
import com.cjf.java.entity.AccountRoleEntity;
import com.cjf.java.entity.FunctionEntity;
import com.cjf.java.entity.RoleEntity;
import com.cjf.java.entity.RoleFunctionEntity;
import com.cjf.java.enums.Whether;
import com.cjf.java.service.AccountService;
import com.cjf.java.service.LoginService;
import com.cjf.java.service.RoleService;
import com.kdl.common.framework.http.JSONResult;

@Controller
@RequestMapping(LoginApi.BASEAPI)
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private LoginService loginService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private NativeCache nativeCache;

	@Autowired
	private RoleService roleService;

	/**
	 * 旧登录接口
	 * 
	 * @param loginDto
	 * @param bindingResult
	 * @param request
	 * @return
	 */
	@RequestMapping(value = LoginApi.LOGIN_OLD, method = RequestMethod.POST)
	public JSONResult loginOld(@RequestBody @Valid LoginDto loginDto, BindingResult bindingResult,
			HttpServletRequest request) {

		if (bindingResult.hasErrors()) {
			logger.info("/login--传入参数为,loginDto:{}", bindingResult.getFieldError().getDefaultMessage());
			return JSONResult.fail(null, bindingResult.getFieldError().getDefaultMessage());
		}

		AccountEntity accountEntity = loginService.login(loginDto);

		if (accountEntity == null) {
			logger.info("用户名密码错误！accountName:{}", loginDto.getAccountName());
			return JSONResult.fail(null, "用户名密码错误！");
		}

		request.getSession().setAttribute("account", accountEntity);
		return JSONResult.success(accountEntity, "登录成功！");
	}

	@RequestMapping(value = LoginApi.LOGINOUT_OLD)
	public JSONResult loginOutOld(HttpServletRequest request) {
		AccountEntity account = (AccountEntity) request.getSession().getAttribute("account");
		if (account != null) {
			logger.info("退出成功！");
			request.getSession().invalidate();
		}
		return JSONResult.success(null, "退出成功！");

	}

	@RequestMapping(value = LoginApi.LOGINOUT)
	public JSONResult loginOut(HttpServletRequest request) {
		LoginAccountCache.remove();
		return JSONResult.success(null, "退出成功！");

	}

	/**
	 * 跳转登录页面
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = LoginApi.LOGIN_PAGE, method = RequestMethod.GET)
	public String loginPage(ModelMap map) {
		System.out.println("ok--------------------------------------");
		return "login";
	}
	
	/**
	 * 跳转登录页面
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(ModelMap map) {
		System.out.println("ok--------------------------------------");
		return "index";
	}

	/**
	 * 新登录接口
	 * 
	 * @param loginDto
	 * @param bindingResult
	 * @param request
	 * @return
	 */
	// @RequestMapping(value = LoginApi.LOGIN, method = RequestMethod.POST)
	public JSONResult login(@RequestBody @Valid LoginDto loginDto, BindingResult bindingResult,
			HttpServletRequest request) {

		if (bindingResult.hasErrors()) {
			logger.info("/login--传入参数为,loginDto:{}", bindingResult.getFieldError().getDefaultMessage());
			return JSONResult.fail(null, bindingResult.getFieldError().getDefaultMessage());
		}

		try {

			AccountEntity account = accountService.getAccount(loginDto.getAccountName(), loginDto.getPassword());

			if (account == null) {
				logger.info("用户名密码错误！accountName:{}", loginDto.getAccountName());
				return JSONResult.fail(null, "用户名密码错误！");
			}

			// 缓存用户信息
			LoginAccountCache.put(account, 30 * 60);

			if (Objects.equals("admin", account.getAccountName())) {
				return JSONResult.success(getAccordions(true, null), "登录成功!");
			} else {
				List<AccountRoleEntity> accountRoles = accountService.getAccountRolesByAccountId(account.getId());
				if (null == accountRoles || 0 == accountRoles.size()) {
					return JSONResult.fail("登录失败!");
				}
				List<Integer> roleIds = new ArrayList<Integer>();
				for (AccountRoleEntity ar : accountRoles) {
					roleIds.add(ar.getId());
				}
				List<RoleEntity> roles = roleService.getRoles(roleIds);
				nativeCache.setRoles(account.getId(), roles);
				return JSONResult.success(getAccordions(false, account.getId()), "登录成功!");
			}
		} catch (Exception e) {
			LoginAccountCache.remove();
			return JSONResult.fail("登录失败!");
		}
	}

	private List<Accordion> getAccordions(boolean isAdmin, Integer AccountId) {
		Set<String> permissionUrls = new HashSet<>();
		Set<Integer> rootFunctionIdSet = new HashSet<>();

		if (!isAdmin) {
			for (RoleEntity role : nativeCache.getRoles(AccountId)) {
				List<RoleFunctionEntity> roleFunctions = roleService.getRoleFunctions(role.getId());
				for (RoleFunctionEntity roleFunction : roleFunctions) {
					FunctionEntity function = nativeCache.getFunction(roleFunction.getId());
					if (Objects.equals(function.getAccordion(), Whether.YES.getValue())) {
						rootFunctionIdSet.add(function.getId());
					} else {
						permissionUrls.add(function.getUrl());
					}
				}
			}
		}

		Map<Integer, Accordion> accordionMap = new HashMap<>();
		List<Accordion> permissionAccordionSet = new LinkedList<>();

		List<FunctionEntity> functions = nativeCache.getFunctions();

		List<Accordion> rootAccordionSet = new LinkedList<>();
		for (FunctionEntity function : functions) {
			Accordion accordion = new Accordion(function.getId(), function.getParentId(), function.getFunctionName(),
					function.getUrl(), function.getOrder());
			accordionMap.put(function.getId(), accordion);
			if (!isAdmin) {
				if (permissionUrls.contains(function.getUrl())) {
					permissionAccordionSet.add(accordion);
				}
				if (rootFunctionIdSet.contains(function.getId())) {
					rootAccordionSet.add(accordion);
				}
			} else {
				permissionAccordionSet.add(accordion);
				if (Whether.YES.getValue() == function.getAccordion()) {
					rootAccordionSet.add(accordion);
				}
			}
		}

		Collections.sort(rootAccordionSet);
		Collections.sort(permissionAccordionSet);
		for (Accordion accordion : rootAccordionSet) {
			completeAccordion(permissionAccordionSet, accordion);
		}
		return rootAccordionSet;
	}

	private void completeAccordion(List<Accordion> permissionAccordionSet, Accordion rootAccordion) {
		for (Accordion accordion : permissionAccordionSet) {
			if (Objects.equals(accordion.getParentId(), rootAccordion.getId())) {
				rootAccordion.getChildren().add(accordion);
			}

		}
	}

}
