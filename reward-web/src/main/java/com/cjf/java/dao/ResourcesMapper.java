package com.cjf.java.dao;

import java.util.List;

import com.cjf.java.entity.ResourcesEntity;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

public interface ResourcesMapper {

	List<ResourcesEntity> queryVideoList(PageBounds pageBounds);

}
