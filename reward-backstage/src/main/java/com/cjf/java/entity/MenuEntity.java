package com.cjf.java.entity;

import lombok.Data;

@Data
public class MenuEntity extends BaseEntity{
	
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
