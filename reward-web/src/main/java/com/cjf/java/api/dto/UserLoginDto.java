package com.cjf.java.api.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.kdl.common.framework.validated.Phone;

import lombok.Data;

@Data
public class UserLoginDto {

	@NotNull(message="电话号码不能为空！")
	@Max(value=11,message="电话号码不能大于11位")
	@Min(value=11,message="电话号码不能小于11位")
	@Phone
	private String phoneNum;
	
	@NotNull(message="密码不能为空!")
	@Length(max=64,message="密码长度不能超过64字符")
	private String password;
}
