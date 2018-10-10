package com.kdl.data.dao;

import java.io.Serializable;
import java.util.List;

import com.kdl.data.entity.BaseEntity;



public interface CrudDao<T extends BaseEntity<?>> extends BaseDao{

	int insert(T t);
	
	int updateByPrimaryKey(T t);
	
	int updateByPrimaryKeySelective(T t);
	
	T selectByPrimaryKey(Serializable id);
	
	int deleteByPrimaryKey(Serializable id);
	
    int insertSelective(T t);
    
	List<T> findList(T t);



}
