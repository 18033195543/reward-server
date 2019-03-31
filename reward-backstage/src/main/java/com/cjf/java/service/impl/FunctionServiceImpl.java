package com.cjf.java.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjf.java.dao.FunctionMapper;
import com.cjf.java.entity.FunctionEntity;
import com.cjf.java.service.FunctionService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FunctionServiceImpl implements FunctionService {

	@Autowired
	private FunctionMapper functionMapper;
	
	@Override
	public void addFunction(FunctionEntity functionEntity) {
		functionMapper.add(functionEntity);
	}

	@Override
	public void updateUrl(Integer id, String url) {
		Map<String, Object> map = new HashMap(2);
		map.put("id", id);
		map.put("url", url);
		functionMapper.updateUrl(map);	
	}

	@Override
	public List<FunctionEntity> getFunctions(int offset, int rows, Integer parentId) {
		Map<String, Object> map = new HashMap(3);
		map.put("offset", offset);
		map.put("rows", rows);
		map.put("parentId", parentId);
		return functionMapper.getFunctions(map);
	}

	@Override
	public void deleteFunctionById(Integer id) {
		functionMapper.deleteFunctionById(id);
	}

	@Override
	public List<FunctionEntity> getAllFunction() {
		return functionMapper.getAllFunction(new HashMap());
	}

}
