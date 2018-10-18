package com.cjf.java.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjf.java.dao.ResourcesMapper;
import com.cjf.java.entity.ResourcesEntity;
import com.cjf.java.service.IResourcesService;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@Service
public class ResourcesServiceImpl implements IResourcesService {

	@Autowired
	private ResourcesMapper resourcesMapper;
	
	@Override
	public List<ResourcesEntity> queryVideoList(PageBounds pageBounds) {
		
		return resourcesMapper.queryVideoList(pageBounds);
	}


}
