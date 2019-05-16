package com.cjf.java.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cjf.java.api.FunctionApI;
import com.cjf.java.api.dto.FunctionDto;
import com.cjf.java.common.Node;
import com.cjf.java.common.Tree;
import com.cjf.java.context.NativeCache;
import com.cjf.java.entity.FunctionEntity;
import com.cjf.java.service.FunctionService;
import com.kdl.common.framework.http.JSONResult;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(FunctionApI.BASE_API)
@Slf4j
public class FunctionController {

	@Autowired
	private FunctionService functionService;

	@Autowired
	private NativeCache nativeCache;

	/**
	 * 新增修改功能
	 * 
	 * @param functionDto
	 * @param bindingResult
	 * @return
	 */
	@PostMapping(FunctionApI.ADD_EDIT_FUNCTION)
	public JSONResult addEditFunction(FunctionDto functionDto, BindingResult bindingResult) {

		if (bindingResult.hasFieldErrors()) {
			log.info("/addFunction 传入参数有误：{}", bindingResult.getFieldError().getDefaultMessage());
			JSONResult.fail(bindingResult.getFieldError().getDefaultMessage());
		}

		FunctionEntity functionEntity = new FunctionEntity();
		BeanUtils.copyProperties(functionDto, functionEntity);

		if (functionEntity.getId() == null) {
			functionEntity.setOrder(nativeCache.getFunctions().size());
			functionService.addFunction(functionEntity);
			nativeCache.addFunction(functionEntity);
		} else {
			functionService.updateUrl(functionEntity.getId(), functionEntity.getUrl());
			nativeCache.replaceFunction(functionEntity);
		}

		return JSONResult.success("新增功能成功！");
	}

	/**
	 * 根据id删除功能信息
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping(FunctionApI.DELETE_FUNCTION)
	public JSONResult deleteFunction(Integer id) {
		functionService.deleteFunctionById(id);
		nativeCache.removeFunction(id);
		return JSONResult.success("功能删除成功!");
	}
	
	/**
	 * 查询子功能信息
	 * @param id
	 * @return
	 */
	@GetMapping(FunctionApI.GET_SUB_FUNCTIONS )
	public JSONResult getSubFunctions(Integer page, Integer rows, Integer parentId) {
		if(Objects.equals(null, parentId)) {
			parentId = 0;
		}
		return JSONResult.success(functionService.getFunctions(page, rows, parentId), "查询成功!");
	}
	
	/**
	 * 创建菜单树
	 * @return
	 */
	@PostMapping(FunctionApI.BUILD_MENU_TREE_FOR_EDIT)
	public List<Node> buildMenuTreeForEdit(){
		List<FunctionEntity> list = nativeCache.getFunctions();
		Tree tree = new Tree(list);
		return tree.build();
	}
	
}
