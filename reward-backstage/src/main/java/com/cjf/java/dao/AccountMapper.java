package com.cjf.java.dao;

import java.util.List;
import java.util.Map;

import com.cjf.java.api.dto.AccountDto;
import com.cjf.java.entity.AccountEntity;

public interface AccountMapper {

	AccountEntity getAccountByOldPasswrod(AccountDto accountDto);

	int updateAccount(AccountEntity accountEntity);
	
	int add(AccountEntity accountEntity);
	
	int delete(Integer id);
	
	AccountEntity getAccount(String name, String pwd);
	
	List<AccountEntity> getAccounts(Map map);

	List<AccountEntity> getAccounts(List<Integer> ids);

}
