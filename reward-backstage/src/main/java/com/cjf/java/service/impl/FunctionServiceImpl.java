package com.cjf.java.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cjf.java.entity.FunctionEntity;
import com.cjf.java.service.FunctionService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FunctionServiceImpl implements FunctionService {

	@Override
	public void addFunction(FunctionEntity functionEntity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUrl(Integer id, String url) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<FunctionEntity> getFunctions(int page, int size, Integer parentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteFunctionById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<FunctionEntity> getAllFunction() {
		// TODO Auto-generated method stub
		return null;
	}

}
