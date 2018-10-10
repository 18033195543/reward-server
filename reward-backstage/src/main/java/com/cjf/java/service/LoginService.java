package com.cjf.java.service;

import com.cjf.java.api.dto.LoginDto;
import com.cjf.java.entity.AccountEntity;

public interface LoginService {

	AccountEntity login(LoginDto loginDto);

}
