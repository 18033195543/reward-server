package com.cjf.java.entity;

import lombok.Data;

@Data
public class FunctionEntity extends BaseEntity{
	private Integer id;
	private String menuName;
	private Integer parentId;
	private Integer level;
	private String url;
	private Integer state;
	private Integer order;
	private String remarks;
	private Long insertTime;
	private Long updateTime;
}
