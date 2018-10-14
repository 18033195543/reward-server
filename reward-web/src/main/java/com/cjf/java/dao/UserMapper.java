package com.cjf.java.dao;

import com.cjf.java.api.dto.UserLoginDto;
import com.cjf.java.api.dto.UserRegisterDto;
import com.cjf.java.api.vo.UserAccountInfoVo;
import com.cjf.java.entity.UserEntity;

public interface UserMapper {

	int queryRegisterPhoneNumByPhoneNum(String phoneNum);

	int addUser(UserEntity userEntity);

	UserEntity queryUserByNameAndPassword(UserLoginDto userLoginDto);

	UserAccountInfoVo querUserInfoByUserId(Integer id);

}
