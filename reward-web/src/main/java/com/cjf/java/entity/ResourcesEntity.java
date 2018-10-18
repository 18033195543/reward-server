package com.cjf.java.entity;

import lombok.Data;

@Data
public class ResourcesEntity {

	private Integer id;
	private Integer accountId;
	private String videoName;
	private String extensionName;
	private Long videoSize;
	private String format;
	private Integer buyCount;
	private Integer price;
	private Integer valid;
	private Long inputTime;
	private Long updateTime;

}
