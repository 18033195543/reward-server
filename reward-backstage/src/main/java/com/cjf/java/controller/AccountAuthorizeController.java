package com.cjf.java.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cjf.java.api.dto.AccountDto;
import com.cjf.java.api.dto.Authorize;
import com.cjf.java.api.dto.AuthorizeApi;
import com.cjf.java.entity.AccountEntity;
import com.cjf.java.entity.AccountRoleEntity;
import com.cjf.java.entity.BaseEntity;
import com.cjf.java.entity.RoleEntity;
import com.cjf.java.service.AccountService;
import com.cjf.java.service.RoleService;
import com.kdl.common.framework.http.JSONResult;

@Controller
@RequestMapping(AuthorizeApi.BASE_API)
public class AccountAuthorizeController {

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private RoleService roleService;
	
	@GetMapping(AuthorizeApi.ACCOUNT_LIST)
	public String accountList(ModelMap map) {
		return "limitsOfAuthority/accountList2";
	}
	
	/**
	 * 查询权限信息
	 * @param offset
	 * @param rows
	 * @return
	 */
	@GetMapping(AuthorizeApi.GET_AUTHORIZES)
	public JSONResult getAuthorizes(Integer offset, Integer rows){
		List<AccountRoleEntity> accountRoles = accountService.getAccountRoles(offset, rows);
		List<Integer> accountIds = new ArrayList<Integer>();
		List<Integer> roleIds = new ArrayList<Integer>();
		accountRoles.forEach((account)->{
			accountIds.add(account.getAccountId());
			roleIds.add(account.getRoleId());
		});
		
		List<AccountEntity> accounts = accountService.getAccounts(accountIds);
		List<RoleEntity> roles = roleService.getRoles(roleIds);
		
		Map<Integer, AccountEntity> accountMap = BaseEntity.idEntityMap(accounts);
		Map<Integer, RoleEntity> roleMap = BaseEntity.idEntityMap(roles);
		
		List<Authorize> authorizes = new LinkedList<>();
		accountRoles.forEach((ar)->{
			Authorize authorize = new Authorize();
			authorize.setAccountId(ar.getAccountId());
			authorize.setRoleId(ar.getRoleId());
			authorize.setAccountRoleId(ar.getId());
			authorize.setRoleName(roleMap.get(ar.getRoleId()).getRoleName());
			authorize.setAccountName(accountMap.get(ar.getAccountId()).getAccountName());
			authorizes.add(authorize);
		});
		
		return JSONResult.success(authorizes, "查询成功！");
	}

	/**
	 * 根据账号id查询账号角色的对应关系
	 * @param accountId
	 * @return
	 */
	@GetMapping(AuthorizeApi.GET_ACCOUNTROLE_BY_ACCOUNTID)
	public JSONResult getAccountRoleByAccountId(Integer accountId) {
		return JSONResult.success(accountService.getAccountRolesByAccountId(accountId), "查询成功!");
	}
	
	/**
	 * 设置权限
	 * @param accountDto
	 * @param roleIds
	 * @return
	 */
	@PostMapping(AuthorizeApi.SET_AUTHORIZE)
	public JSONResult setAuthorize(AccountDto accountDto, String roleIds) {
		String[] temp = roleIds.split(",");
		Integer [] roleIdArray = new Integer[temp.length];
		
		for(int i = 0; i < roleIdArray.length; i++) {
			roleIdArray[i] = Integer.valueOf(temp[i]);
		}
		
		accountService.addAccountRoles(accountDto.getId(), roleIdArray);
		return JSONResult.success("操作成功！");
	}
	
}
