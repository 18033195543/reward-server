package com.cjf.java.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class WebApplicationContext implements ApplicationContextAware {

	private static ApplicationContext ctx;
	
	public WebApplicationContext() {}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ctx = applicationContext;
	}

	public static<T> T getBean(Class clazz) {
		return (T) ctx.getBean(clazz);
	}
}
