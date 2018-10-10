package com.cjf.java.dao;

import com.cjf.java.api.dto.AccountDto;
import com.cjf.java.entity.AccountEntity;

public interface AccountMapper {

	AccountEntity getAccountByOldPasswrod(AccountDto accountDto);

	int updateAccount(AccountEntity accountEntity);

}
