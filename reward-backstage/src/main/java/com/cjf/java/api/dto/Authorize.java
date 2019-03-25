package com.cjf.java.api.dto;

import lombok.Data;

@Data
public class Authorize {

	private Integer accountId;
	private Integer accountRoleId;
	private Integer roleId;
	private String AccountName;
	private String roleName;
}
