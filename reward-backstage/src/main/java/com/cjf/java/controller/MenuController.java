package com.cjf.java.controller;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cjf.java.api.MenuApi;
import com.cjf.java.api.dto.MenuDto;
import com.cjf.java.entity.MenuEntity;
import com.cjf.java.service.MenuService;
import com.kdl.common.framework.http.JSONResult;

import lombok.extern.slf4j.Slf4j;
/**
 * 菜单controller类
 * @author jfyus
 *
 */
@RestController
@RequestMapping(MenuApi.BaseApi)
@Slf4j
public class MenuController {

	@Autowired
	private MenuService menuServiceImpl;
	
	
	/**
	 * 新增菜单
	 * @return
	 */
	@RequestMapping(value=MenuApi.ADD_MENU, method = RequestMethod.POST)
	public JSONResult addMenu(@RequestBody @Valid MenuDto menuDto, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			log.info("新增菜单传入参数有误：{}",bindingResult.getFieldError().getDefaultMessage());
			return JSONResult.fail(null, bindingResult.getFieldError().getDefaultMessage());
		}
		
		MenuEntity menuEntity = new MenuEntity();
		BeanUtils.copyProperties(menuDto, menuEntity);
		
		
		boolean addMenu = menuServiceImpl.addMenu(menuEntity);
		
		if(addMenu) {
			log.info("新增菜单成功！result:{}",addMenu);
			return JSONResult.success(null, "新增菜单成功！");
		}
		
		log.info("新增菜单失败！result:{}",addMenu);
		return JSONResult.fail(null, "新增菜单失败！");
	}
}
