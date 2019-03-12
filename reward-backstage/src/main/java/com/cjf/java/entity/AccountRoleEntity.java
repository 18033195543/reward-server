package com.cjf.java.entity;

import lombok.Data;

@Data
public class AccountRoleEntity extends BaseEntity{
	private Integer id;
	private Integer accountId;
	private Integer roleId;
	private Long insertTime;
	private Long updateTime;
}
