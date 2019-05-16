package com.cjf.java.context;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import com.cjf.java.api.dto.Accordion;
import com.cjf.java.entity.AccountEntity;

public class LoginAccountCache {

	private static Map<String, AccountEntity> cache = new HashMap<String,AccountEntity>();
	private static Map<String, List<Accordion>> accountAccordionsMap = new HashMap<String, List<Accordion>>();
	
	
	public static void put(AccountEntity account) {
		cache.put(account.getAccountName(), account);
		AccountContext.setCurrent(account);
		setCookie(account);
	}
	
	public static AccountEntity get(String key) {
		return cache.get(key);
	}
	
	public static void setCookie(AccountEntity account) {
		int expire = 1800;
		String source = account.getAccountName()+"$"+account.getPassword();
		byte[] result = Base64.getEncoder().encode(source.getBytes());
		Cookie cookie = new Cookie("auth", new String(result));
		cookie.setPath("/");
		cookie.setMaxAge(expire);
		ResponseContext.getCurrent().addCookie(cookie);
	}

	public static void remove(String name) {
		cache.remove(name);
		Cookie cookie = new Cookie("auth", null);
		ResponseContext.getCurrent().addCookie(cookie);
		AccountContext.setCurrent(null);
	}
	
	public static void setAccordions(String accountName, List<Accordion> accordions) {
		accountAccordionsMap.put(accountName, accordions);
	} 
	
	public static List<Accordion> getAccordions(String accountName) {
		return accountAccordionsMap.get(accountName);
	}


}
