package com.cjf.java.api.dto;

import lombok.Data;

@Data
public class RoleDto {
	private Integer id;
	private String roleName;
	private String roleDescribe;
	private Integer roleType;
	private Integer busiNum;
	private Integer order;
	private String functionIds;
	private Long insertTime;
	private Long updateTime;
}
