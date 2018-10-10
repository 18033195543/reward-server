package com.kdl.data.entity;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TreeEntity extends BaseEntity {

	
	private static final long serialVersionUID = 1L;

	/**
	 * 父部门
	 */
	private String parentId;
	
	/**
	 * 父Ids ，按层级逗号分离
	 */
	private String parentIds;

}
