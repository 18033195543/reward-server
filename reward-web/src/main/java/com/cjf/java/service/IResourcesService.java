package com.cjf.java.service;

import java.util.List;

import com.cjf.java.entity.ResourcesEntity;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

public interface IResourcesService {

	List<ResourcesEntity>  queryVideoList(PageBounds pageBounds);

}
