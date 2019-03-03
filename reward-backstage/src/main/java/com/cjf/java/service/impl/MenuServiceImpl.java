package com.cjf.java.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjf.java.dao.MenuMapper;
import com.cjf.java.entity.MenuEntity;
import com.cjf.java.service.MenuService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuMapper menuMapper;
	
	@Override
	public boolean addMenu(MenuEntity menuEntity) {
		menuEntity.setInsertTime(System.currentTimeMillis());
		
		int addMenu = menuMapper.addMenu(menuEntity);
		
		log.info("新增菜单数为:{}",addMenu);
		if(addMenu > 0) {
			return true;
		}
		
		return false;
	}

}
