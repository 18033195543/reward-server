package com.cjf.java.dao;

import java.util.List;
import java.util.Map;

import com.cjf.java.entity.AccountRoleEntity;

public interface AccountRoleMapper {

	List<AccountRoleEntity> getAccountRoles(Map map);

	List<AccountRoleEntity> getAccountRolesByAccountId(Integer id);

	void addAccountRoles(List<AccountRoleEntity> accountRoleList);

}
