package com.cjf.java.dao;

import com.cjf.java.api.dto.LoginDto;
import com.cjf.java.entity.AccountEntity;

public interface LoginMapper {

	AccountEntity getAccountByAccount(LoginDto loginDto);

}
