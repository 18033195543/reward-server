package com.cjf.java.api.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
public class AccountDto {

	@NotBlank(message="用户名不能为空")
	@Length(max=20,min=1,message="用户名只能为1-20的长度")
	private String accountName;
	
	@NotBlank(message="新密码不能为空")
	@Length(max=50,min=6,message="新密码只能为6-50的长度")
	private String password;
	
	@NotBlank(message="确认密码不能为空")
	@Length(max=50,min=6,message="确认密码只能为6-50的长度")
	private String confirmPassword;
	
	@NotBlank(message="旧密码不能为空")
	@Length(max=50,min=6,message="旧密码只能为6-50的长度")
	private String oldPassword;
	
	private Integer accountType;
	
	private Integer accountStatus;
	
	private Long updateTime;
}
