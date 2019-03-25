package com.cjf.java.context;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.cjf.java.entity.AccountEntity;

public class LoginAccountCache {

	private static Map<Integer, LoginAccount> cache = new HashMap<Integer,LoginAccount>();
	
	public static AccountEntity get() {
		return null;
		
	}
	
	/**
	 * 
	 * @param account
	 * @param expire 单位秒，如果是30分钟过期，即：60*30=1800
	 */
	public static void put (AccountEntity account,long expire) {
		long expireTime = Calendar.getInstance().getTime().getTime() + expire * 1000;
		LoginAccount loginAccount = new LoginAccount();
		loginAccount.setExpire(expire);
		loginAccount.setAccountEntity(account);
		cache.put(account.getId(), loginAccount);
	}

	public static void remove() {
		
	}
	
	private static class LoginAccount{
		private long expire;
		private AccountEntity accountEntity;
		public long getExpire() {
			return expire;
		}
		public void setExpire(long expire) {
			this.expire = expire;
		}
		public AccountEntity getAccountEntity() {
			return accountEntity;
		}
		public void setAccountEntity(AccountEntity accountEntity) {
			this.accountEntity = accountEntity;
		}
		
	}
}
