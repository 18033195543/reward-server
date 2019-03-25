package com.cjf.java.entity;

import lombok.Data;

@Data
public class FunctionEntity extends BaseEntity{
	private String FunctionName;
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
