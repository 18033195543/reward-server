package com.cjf.java.api.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.kdl.common.framework.validated.Email;
import com.kdl.common.framework.validated.Phone;

import lombok.Data;

@Data
public class UserRegisterDto {

	@NotNull(message="姓名不能为空!")
	@Length(max=64,message="姓名长度不能超过64字符")
	private String realName;
	
	@NotNull(message="电话号码不能为空！")
//	@Max(value=11,message="电话号码不能大于11位")
//	@Min(value=11,message="电话号码不能小于11位")
	@Phone
	private String phoneNum;
	
	@NotNull(message="密码不能为空!")
	@Length(max=64,min=6,message="密码长度不正确！")
	private String password;
	
	@NotNull(message="邮箱不能为空!")
	@Email
	private String email;
	
	private Integer accountId;
	
	private Integer status;
	
}
