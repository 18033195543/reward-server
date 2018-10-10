package com.cjf.java.api.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginDto {

	@NotBlank(message="用户名不能为空")
	@Length(max=20,min=1,message="用户名只能为1-20的长度")
	private String accountName;
	
	@NotBlank(message="密码不能为空")
	@Length(max=50,min=6,message="密码只能为6-50的长度")
	private String password;
	
	private String smsCode;
}
