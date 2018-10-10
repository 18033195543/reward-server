package com.kdl.data;

import java.security.SecureRandom;
import java.util.UUID;

public class IdGen {

	private static SecureRandom  random = new SecureRandom();
	
	public static String uuid() {
			return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * 使用SecureRandom随机生成Long
	 * @return
	 */
	public static long randomLong() {
		return Math.abs(random.nextLong());
	}
}
