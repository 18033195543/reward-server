package com.cjf.java.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjf.java.api.dto.RoleDto;
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
	public void addRole(RoleDto roleDto) {
		String functionIds = roleDto.getFunctionIds();
		Integer roleId = roleDto.getId();
		String[] idArray = functionIds.split(",");
		List<RoleFunctionEntity> roleFunctionEntitys = new ArrayList<>();
		Long inputTime = System.currentTimeMillis();
		for(int i = 0; i < idArray.length; i++) {
			RoleFunctionEntity roleFunctionEntity = new RoleFunctionEntity();
			roleFunctionEntity.setRoleId(roleId);
			roleFunctionEntity.setFunctionId(Integer.valueOf(idArray[i]));
			roleFunctionEntity.setInsertTime(inputTime);
			roleFunctionEntitys.add(roleFunctionEntity);
		}
		
		RoleEntity roleEntity = new RoleEntity();
		BeanUtils.copyProperties(roleDto, roleEntity);
		roleEntity.setInsertTime(inputTime);
		
		roleMapper.addRole(roleEntity);
		roleFunctionMapper.addRoleFunction(roleFunctionEntitys);
	}

	@Override
	public void updateRole(RoleDto role) {
		Integer roleId = role.getId();
		// 更新role
		RoleEntity roleEntity = new RoleEntity();
		BeanUtils.copyProperties(role, roleEntity);
		roleMapper.updateRole(roleEntity);
		// 删除rolefunction
		roleFunctionMapper.deleteRoleFunctionById(roleId);
		// 插入新的roleFunction
		String functionIds = role.getFunctionIds();
		String[] idArray = functionIds.split(",");
		List<RoleFunctionEntity> roleFunctionEntitys = new ArrayList<>();
		Long inputTime = System.currentTimeMillis();
		for(int i = 0; i < idArray.length; i++) {
			RoleFunctionEntity roleFunctionEntity = new RoleFunctionEntity();
			roleFunctionEntity.setRoleId(roleId);
			roleFunctionEntity.setFunctionId(Integer.valueOf(idArray[i]));
			roleFunctionEntity.setInsertTime(inputTime);
			roleFunctionEntitys.add(roleFunctionEntity);
		}
		roleFunctionMapper.addRoleFunction(roleFunctionEntitys);
	}

	@Override
	public void deleteRole(Integer roleId) {
		roleMapper.deleteRole(roleId);		
		roleFunctionMapper.deleteRoleFunctionById(roleId);		
	}

	@Override
	public List<RoleDto> getRoles(int offset, int rows) {
		Map<String, Object> map = new HashMap<>();
		map.put("offset",offset);
		map.put("rows",rows);
		List<RoleEntity> roles = roleMapper.getRoles(map);
		List<Integer> roleIds = new ArrayList<>();
		List<RoleDto> roleDtos = new ArrayList<>();
		
		roles.forEach((role)->{
			roleIds.add(role.getId());
			RoleDto roleDto = new RoleDto();
			BeanUtils.copyProperties(role, roleDto);
			roleDtos.add(roleDto);
		});
		
		List<RoleFunctionEntity> roleFunctions = roleFunctionMapper.findRoleFunctionByRoleIds(roleIds);
		if(roles != null && roles.size() > 0 && roleFunctions != null && roleFunctions.size() > 0) {
			
			roleDtos.forEach((item)->{
				StringBuffer sb = new StringBuffer();
				roleFunctions.forEach((roleFunction)->{
					if(item.getId() == roleFunction.getRoleId()) {
						sb.append(roleFunction.getId()).append(",");
					}
				});
				if(sb.length() > 1) {
					item.setFunctionIds(sb.deleteCharAt(sb.length()-1).toString());
				}
			});
			
		}
		
		return roleDtos;
	}

	@Override
	public List<RoleEntity> getRoles(List<Integer> ids) {
		return null;
	}

	@Override
	public List<FunctionEntity> getRoleFunctions(Integer AccountId) {
		// TODO Auto-generated method stub
		return null;
	}

}
