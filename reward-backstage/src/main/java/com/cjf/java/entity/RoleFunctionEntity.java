package com.cjf.java.entity;

import lombok.Data;

@Data
public class RoleFunctionEntity extends BaseEntity{
	private Integer id;
	private Integer roleId;
	private Integer functionId;
	private Long insertTime;
	private Long updateTime;
}
