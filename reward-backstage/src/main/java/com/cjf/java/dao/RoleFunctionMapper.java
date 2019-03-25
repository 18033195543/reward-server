package com.cjf.java.dao;

import java.util.List;

import com.cjf.java.entity.RoleFunctionEntity;

public interface RoleFunctionMapper {

	void addRoleFunction(List<RoleFunctionEntity> roleFunctionEntitys);

	void deleteRoleFunctionById(Integer id);

	List<RoleFunctionEntity> findRoleFunctionByRoleIds(List<Integer> roleIds);

	List<RoleFunctionEntity> getRoleFunctions(Integer roleId);

}
