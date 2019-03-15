package com.cjf.java.service;

import java.util.List;

import com.cjf.java.entity.FunctionEntity;
import com.cjf.java.entity.RoleEntity;
import com.cjf.java.entity.RoleFunctionEntity;

public interface RoleService {

	/**
	 * 保存角色信息，同时保存角色对应的功能
	 * 
	 * @param role
	 * @param rolefunction
	 */
	void addRole(RoleEntity role, List<RoleFunctionEntity> rolefunction);

	/**
	 * 修改角色信息，同时保存角色对应的功能
	 * 
	 * @param role
	 * @param rolefunction
	 */
	void updateRole(RoleEntity role, List<RoleFunctionEntity> rolefunction);

	/**
	 * 根据id删除角色
	 * @param roleId
	 */
	void deleteRole(Integer roleId);
	
	/**
	 * 分页查询角色信息
	 * @param page
	 * @param size
	 * @return
	 */
	List<RoleEntity> getRoles(int page, int size);

	/**
	 * 根据角色id集合查询角色信息
	 * @param page
	 * @param size
	 * @return
	 */
	List<RoleEntity> getRoles(List<Integer> ids);
	
	/**
	 * 根据用户id查询用户功能对应关系
	 * @param AccountIdO
	 * @return
	 */
	List<FunctionEntity> getRoleFunctions(Integer AccountIdO);
	
}
