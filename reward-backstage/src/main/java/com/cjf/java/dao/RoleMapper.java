package com.cjf.java.dao;

import java.util.List;
import java.util.Map;

import com.cjf.java.entity.RoleEntity;

public interface RoleMapper {

	void addRole(RoleEntity roleEntity);

	void updateRole(RoleEntity roleEntity);

	void deleteRole(Integer roleId);

	List<RoleEntity> getRoles(Map<String, Object> map);

}
