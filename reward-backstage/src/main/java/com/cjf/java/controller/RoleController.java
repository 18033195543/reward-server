package com.cjf.java.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cjf.java.api.dto.RoleDto;
import com.cjf.java.entity.RoleEntity;
import com.cjf.java.entity.RoleFunctionEntity;
import com.cjf.java.service.RoleService;
import com.kdl.common.framework.http.JSONResult;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/role")
@Slf4j
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@GetMapping("/getRoles")
	public JSONResult getRoles(Integer page, Integer rows) {
		List<RoleEntity> roles = roleService.getRoles(page, rows);
		return JSONResult.success(roles, "获取角色成功!");
	}
	
	@PostMapping("/addEditRole")
	public JSONResult addEditRole(@Valid RoleDto roleDto, BindingResult bindingResult) {
		if(bindingResult.hasFieldErrors()) {
			log.info("/addEditRole ---传入参数有误：{}",bindingResult.getFieldError().getDefaultMessage());
			return JSONResult.fail(null, bindingResult.getFieldError().getDefaultMessage());
		}
		String functionIds = roleDto.getFunctionIds();
		String[] idArray = functionIds.split(",");
		List<RoleFunctionEntity> roleFunctionEntitys = new ArrayList<>();
		
		for(int i = 0; i < idArray.length; i++) {
			RoleFunctionEntity roleFunctionEntity = new RoleFunctionEntity();
			roleFunctionEntity.setFunctionId(Integer.valueOf(idArray[i]));
			roleFunctionEntitys.add(roleFunctionEntity);
		}
		
		RoleEntity roleEntity = new RoleEntity();
		BeanUtils.copyProperties(roleDto, roleEntity);
		
		if(roleDto.getId() == null) {
			roleService.addRole(roleEntity, roleFunctionEntitys);
		}else {
			roleService.updateRole(roleEntity, roleFunctionEntitys);
		}
		
		return JSONResult.success(null, "操作成功!");
	}
	
	@PostMapping("/deleteRole")
	public JSONResult deleteRole(Integer id) {
		roleService.deleteRole(id);
		return JSONResult.success(null, "删除角色成功!");
		
	}
	
}
