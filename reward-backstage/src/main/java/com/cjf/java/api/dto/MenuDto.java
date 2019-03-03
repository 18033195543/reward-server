package com.cjf.java.api.dto;

import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
public class MenuDto {

	@NotBlank(message = "菜单名不能为空")
	@Size(max=128,min=0 , message = "菜单名不能超过128")
	private String menuName;

	private Integer parentId;
	
	@Max(value = 5, message = "等级最大只能为5")
	@Min(value = 0, message = "等级最小只能为0")
	private int level;

	@Size(max = 256, message = "url最大只能为256")
	private String url;

	@Max(value = 1, message = "等级最大只能为1")
	@Min(value = 0, message = "等级最小只能为0")
	private int state;

	private Integer order;

	@Size(max = 256, message = "备注最大只能为256")
	private String remarks;
}
