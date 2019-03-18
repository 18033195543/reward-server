package com.cjf.java.context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjf.java.entity.FunctionEntity;
import com.cjf.java.entity.RoleEntity;
import com.cjf.java.service.FunctionService;

@Service
public class NativeCache {

	private Map<Integer,FunctionEntity> functionMap = new HashMap<>();
	private Map<Integer,List<RoleEntity>> accountRoleMap = new HashMap<>();
	
	@Autowired
	private FunctionService functionService;
	
	@PostConstruct
	public void init() {
		List<FunctionEntity> functionList = functionService.getAllFunction();
		functionList.forEach((function) -> functionMap.put(function.getId(), function));
	}
	
	public List<FunctionEntity> getFunctions(){
		if(functionMap.isEmpty()) {
			init();
		}
		return new ArrayList<>(functionMap.values());
	}
	
	public void addFunction(FunctionEntity function) {
		functionMap.put(function.getId(), function);
	}
	
	public void replaceFunction(FunctionEntity function) {
		if(functionMap.containsKey(function.getId())) {
			functionMap.remove(function.getId());
			functionMap.put(function.getId(), function);
		}
	}
	
	public void removeFunction(Integer functionId) {
		if(functionMap.containsKey(functionId)) {
			functionMap.remove(functionId);
		}
	}
	
	public void setRoles(Integer accountId, List<RoleEntity> roles) {
		accountRoleMap.put(accountId, roles);
	}
	
	public List<RoleEntity> getRoles(Integer accountId){
		return accountRoleMap.get(accountId);
	}
	
	public FunctionEntity getFunction(Integer functionId){
		return functionMap.get(functionId);
	}
}
