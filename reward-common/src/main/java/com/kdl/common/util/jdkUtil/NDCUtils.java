package com.kdl.common.util.jdkUtil;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.NDC;

/**
 * 用来跟踪日志
 *
 */
public class NDCUtils {
	
	public static void clear() {
		NDC.clear();
	}
	
	public static String peek() {
		String peek = NDC.peek();
		return StringUtils.isNotEmpty(peek)?peek:null;
	}
	
	public static void push() {
		clear();
		NDC.push(randomThreadId());
	}
	
	private static String randomThreadId() {
		return RandomStringUtils.randomAlphanumeric(10);
	}
}
