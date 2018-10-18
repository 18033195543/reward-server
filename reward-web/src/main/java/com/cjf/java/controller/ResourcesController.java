package com.cjf.java.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cjf.java.api.ResourcesApi;
import com.cjf.java.entity.ResourcesEntity;
import com.cjf.java.service.IResourcesService;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.kdl.common.framework.http.JSONResult;

import lombok.extern.slf4j.Slf4j;

/**
 * Resources controller class,
 * @author jfyus
 *
 */

@RestController
@RequestMapping(ResourcesApi.BASEAPI)
@Slf4j
public class ResourcesController {

	@Autowired
	private IResourcesService resourcesService;
	
	/**
	 * Home page use query video list and paging
	 * @return
	 */
	@RequestMapping(value = ResourcesApi.QUERY_VIDEO_LIST, method = RequestMethod.GET)
	public JSONResult queryVideoList(@RequestParam(value = "currPage", defaultValue = "1") Integer currPage,
			@RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize) {		
		PageBounds pageBounds = new PageBounds(currPage,pageSize);
		
		log.info("开始查询~~~");
		List<ResourcesEntity> resourcesEntityList = resourcesService.queryVideoList(pageBounds);
		log.info("查询结束!");
		return JSONResult.success(resourcesEntityList, "查询成功！");
	} 
	
}
