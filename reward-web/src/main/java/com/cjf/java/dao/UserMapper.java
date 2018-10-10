package com.cjf.java.dao;

import com.cjf.java.api.dto.UserRegisterDto;
import com.cjf.java.entity.UserEntity;

public interface UserMapper {

	UserEntity queryUserByPhoneNumPassword(UserRegisterDto userDto);

	int queryRegisterPhoneNumByPhoneNum(String phoneNum);

	int addUser(UserEntity userEntity);

}
