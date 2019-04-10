package com.cjf.java.context;

import com.cjf.java.entity.AccountEntity;

public class AccountContext {

	private static ThreadLocal<AccountContext> tl = new ThreadLocal<>();
	
	private AccountEntity account;
	
	private AccountContext(AccountEntity account) {
		this.account = account;
	}
	
	public static void setCurrent(AccountEntity account) {
		tl.set(new AccountContext(account));
	}
	
	public static AccountContext getCurrent() {
		return tl.get();
	}
	
	public AccountEntity getAccount() {
		return account;
	}
}
