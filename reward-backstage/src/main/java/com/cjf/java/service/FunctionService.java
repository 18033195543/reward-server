package com.cjf.java.service;

import java.util.List;

import com.cjf.java.entity.FunctionEntity;

public interface FunctionService {

	/**
	 * 
	 * @param functionEntity
	 */
	void addFunction(FunctionEntity functionEntity);
	
	/**
	 * 根据功能id更新其URL信息
	 * @param id
	 * @param url
	 */
	void updateUrl(Integer id, String url);
	
	/**
	 * 分页查询指定父节点的子节点
	 * @param page
	 * @param size
	 * @param parentId
	 * @return
	 */
	List<FunctionEntity> getFunctions(int page, int size, Integer parentId);
	
	/**
	 * 根据id删除功能
	 * @param id
	 */
	void deleteFunctionById(Integer id);
	
	/**
	 * 查询全部功能信息
	 * @return
	 */
	List<FunctionEntity> getAllFunction();
}
