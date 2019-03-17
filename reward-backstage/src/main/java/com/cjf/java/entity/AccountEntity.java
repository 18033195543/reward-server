package com.cjf.java.entity;

import lombok.Data;

@Data
public class AccountEntity extends BaseEntity {

	private String accountName;
	private String password;
	private Integer accountType;
	private Integer accountStatus;
	private Long inputTime;
	private Long updateTime;
}
