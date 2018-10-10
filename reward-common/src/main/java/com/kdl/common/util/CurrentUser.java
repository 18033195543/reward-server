package com.kdl.common.util;

import com.kdl.common.framework.enums.SystemTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CurrentUser {
	private int id;
	private String uid;
	private String account;
	private SystemTypeEnum systemType; 
}
