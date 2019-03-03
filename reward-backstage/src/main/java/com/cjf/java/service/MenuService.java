package com.cjf.java.service;

import com.cjf.java.entity.MenuEntity;

public interface MenuService {

	/**
	 * 新增菜单
	 * @param dto
	 * @return
	 */
	boolean addMenu(MenuEntity dto);
}
