package com.kdl.common.framework.file;

import org.springframework.beans.factory.annotation.Value;


/**
 * 文件配置信息
 * @author Administrator
 *
 */
public class FileConfig {
	
	/**
	 * 文件存储方式：本地，阿里云，七牛
	 */
	@Value("${file.storage}")
	private String storeage;
	
	/**
	 * 本地公网访问前缀
	 */
	@Value("${file.local.urlPrefix}")
	private String localUrlPrefix;
	
	/*
	 * 本地存储路径
	 */
	@Value("${file.local.storePath}")
	private String localStorePath;
	
	/**
	 * 阿里云公网访问前缀
	 */
	@Value("${file.aliyun.urlPrefix}")
	private String aliyunUrlPrefix ;
	/**
	 * 阿里云bucket 即存储路径
	 */
	@Value("${file.aliyun.bucket}")
	private String aliyunBucket;

	@Value("${file.aliyun.endpoint}")
	private String aliyunEndpoint;
	
	@Value("${file.aliyun.accessKeyId}")
	private String aliyunAccessKeyId;
	
	@Value("${file.aliyun.accessKeySecret}")
	private String aliyunAccessKeySecret;
	
	
	
	
	

}
