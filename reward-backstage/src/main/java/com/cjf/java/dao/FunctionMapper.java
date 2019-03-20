package com.cjf.java.dao;

import java.util.List;
import java.util.Map;

import com.cjf.java.entity.FunctionEntity;

public interface FunctionMapper {

	void add(FunctionEntity functionEntity);

	void updateUrl(Map map);

	List<FunctionEntity> getFunctions(Map<String, Object> map);

	void deleteFunctionById(Integer id);

	List<FunctionEntity> getAllFunction();

}
