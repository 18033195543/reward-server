package com.cjf.java.entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BaseEntity {

	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public static <T extends BaseEntity> Map<Integer, T> idEntityMap(Collection<T> list){
		Map<Integer, T> map = new HashMap<>();
		if(list == null || list.size() == 0) {
			return map;
		}
		
		for(T entity:list) {
			map.put(entity.getId(), entity);
		}
		return map;
		
	}
}
