package com.cjf.java.api.dto;

import lombok.Data;

@Data
public class FunctionDto {
	private String menuName;
	private Integer parentId;
	private Integer level;
	private String url;
	private Integer state;
	private Integer order;
	private Integer accordion;
	private String remarks;
	private Long insertTime;
	private Long updateTime;
}
