package com.cjf.java.entity;

import lombok.Data;

@Data
public class RoleEntity extends BaseEntity{
	private Integer id;
	private String roleName;
	private String roleDescribe;
	private Integer roleType;
	private Integer busiNum;
	private Integer order;
	private Long insertTime;
	private Long updateTime;
}
