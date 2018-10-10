package com.kdl.common.util;

public class UserContextUtil {

	private static ThreadLocal<CurrentUser> userContext = new ThreadLocal<CurrentUser>();

	public static String getUserId() {
		CurrentUser currentUser = userContext.get();
		if (currentUser == null) {
			return null;
		}
		return userContext.get().getUid();
	}

	public static void setUser(CurrentUser user) {
		userContext.set(user);
	}

	public static void remove() {
		userContext.remove();
	}

}
