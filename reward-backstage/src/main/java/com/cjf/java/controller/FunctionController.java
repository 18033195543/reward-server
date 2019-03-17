package com.cjf.java.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cjf.java.api.FunctionApI;
import com.cjf.java.api.dto.FunctionDto;
import com.cjf.java.entity.FunctionEntity;
import com.cjf.java.service.FunctionService;
import com.kdl.common.framework.http.JSONResult;

import lombok.extern.slf4j.Slf4j;

@RequestMapping(FunctionApI.BASE_API)
@Slf4j
public class FunctionController {

	@Autowired
	private FunctionService functionService;
	
	@PostMapping(FunctionApI.ADD_EDIT_FUNCTION)
	public JSONResult addEditFunction(FunctionDto functionDto, BindingResult bindingResult) {
		
		if(bindingResult.hasFieldErrors()) {
			log.info("/addFunction 传入参数有误：{}",bindingResult.getFieldError().getDefaultMessage());
			JSONResult.fail(bindingResult.getFieldError().getDefaultMessage());
		}
		
		FunctionEntity functionEntity = new FunctionEntity();
		BeanUtils.copyProperties(functionDto, functionEntity);
		
		if(functionEntity.getId() == null) {
			functionService.addFunction(functionEntity);
		}else {
			functionService.updateUrl(id, url);
		}
		
		return JSONResult.success("新增功能成功！");
	}
	
}
