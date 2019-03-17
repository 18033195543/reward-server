package com.cjf.java.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjf.java.dao.RoleFunctionMapper;
import com.cjf.java.dao.RoleMapper;
import com.cjf.java.entity.FunctionEntity;
import com.cjf.java.entity.RoleEntity;
import com.cjf.java.entity.RoleFunctionEntity;
import com.cjf.java.service.RoleService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private RoleFunctionMapper roleFunctionMapper;
	
	@Override
	public void addRole(RoleEntity role, List<RoleFunctionEntity> rolefunction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRole(RoleEntity role, List<RoleFunctionEntity> rolefunction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteRole(Integer roleId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<RoleEntity> getRoles(int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RoleEntity> getRoles(List<Integer> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FunctionEntity> getRoleFunctions(Integer AccountIdO) {
		// TODO Auto-generated method stub
		return null;
	}

}
