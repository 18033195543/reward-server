package com.kdl.common.framework.file.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.springframework.util.Assert;

/**
 * 本地文件处理
 *
 */
public class LocalFileHandler implements FileHandler {
	
	private String storePath;
	
	

	public LocalFileHandler(String storePath) {
		Assert.hasLength(storePath, "storePath不能为空");
		this.storePath = storePath;
	}

	@Override
	public void upload(String relativePath, InputStream in) throws IOException {
		Assert.hasLength(storePath, "storePath不能为空");
		Assert.hasLength(relativePath, "relativePath不能为空");
		Assert.notNull(in, "relativePath不能为空");
		File file = new File(storePath,relativePath);
		FileUtils.copyInputStreamToFile(in, file);
	}

	@Override
	public InputStream download(String relativePath) throws FileNotFoundException {
		Assert.hasLength(relativePath);
		File file = new File(storePath,relativePath);
		FileInputStream in = new FileInputStream(file);
		return in;
	}

	@Override
	public void destory() {

	}

}
