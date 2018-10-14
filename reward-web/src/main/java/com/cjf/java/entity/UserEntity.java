package com.cjf.java.entity;

import lombok.Data;

@Data
public class UserEntity {

	private Integer id;
	private String realName;
	private String phoneNum;
	private String password;
	private String email;
	private Integer operatorId;
	private Integer status;
	private Long inputTime;
	private Long updateTime;
}
