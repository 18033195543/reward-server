package com.kdl.data.entity;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityVO {
	private String code; //省-市-区编号
	private String name;//省-市-区名称
	private String parentCode;//关联的编号
	private List<CityVO> children;
	public CityVO() {
	}
	
	
	
	
}
