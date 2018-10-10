package com.kdl.common.framework.file.handler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileHandler {
	
	/**
	 * 上传
	 * @param relativePath
	 * @param in
	 * @throws IOException
	 */
	public void upload(String relativePath,InputStream in) throws IOException;
	
	/**
	 * 下载
	 * @param relativePath
	 * @return
	 * @throws FileNotFoundException
	 */
	public InputStream download(String relativePath) throws FileNotFoundException;
	
	public void destory();

}
