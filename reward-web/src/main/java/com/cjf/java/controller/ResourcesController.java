package com.cjf.java.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cjf.java.api.ResourcesApi;
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

	/**
	 * Home page use query video list and paging
	 * @return
	 */
	@RequestMapping(value = ResourcesApi.QUERY_VIDEO_LIST, method = RequestMethod.GET)
	public JSONResult queryVideoList() {
		
		
		return null;
	} 
	
}
