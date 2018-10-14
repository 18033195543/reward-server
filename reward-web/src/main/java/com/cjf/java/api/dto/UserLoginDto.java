package com.cjf.java.api.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.kdl.common.framework.validated.Phone;

import lombok.Data;

@Data
public class UserLoginDto {

	@NotNull(message="电话号码不能为空！")
	@Phone
	private String phoneNum;
	
	@NotNull(message="密码不能为空!")
	@Length(max=64,min=6,message="密码长度不正确！")
	private String password;
}
