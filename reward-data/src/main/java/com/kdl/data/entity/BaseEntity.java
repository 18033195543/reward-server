package com.kdl.data.entity;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseEntity<PK> implements Serializable{

	
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID,
	 */
	private PK id;
	
	/**
	 * 创建者（后台人员使用）
	 */
	private String createBy;
	
	/**
	 * 创建时间（后台人员使用）
	 */
	private Date createTime;
	
	/**
	 * 更新者（后台人员使用）
	 */
	private String updateBy;
	
	/**
	 * 更新时间（后台人员）
	 */
	private Date updateTime;
	
	
	/**
	 * 是否删除(逻辑删除)，默认未删除
	 */
	private Boolean isDeleted = false;
	
	/**
	 * 备注信息
	 */
	private String desc;
	
	/**
	 * 是否需要备份
	 */
	@JSONField(serialize=false)
	private boolean isNeedBak = false;

}
