package com.kdl.common.framework.file;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * 文件信息
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class FileInfo {
	
	/**
	 * 文件全路径
	 */
	private String fullUrl;
	
	/**
	 * 文件相对路径
	 */
	private String relativePath;
	
	/**
	 * 原始文件名
	 */
	private String originalFileName;
	
}
