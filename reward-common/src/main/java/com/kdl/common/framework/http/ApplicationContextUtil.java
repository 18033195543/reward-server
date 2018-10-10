package com.kdl.common.framework.http;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;

import com.google.common.collect.Lists;

/**
 * 获取Spring容器里Bean对象
 */
public class ApplicationContextUtil implements ApplicationContextAware {

    private  static  ApplicationContext applicationContext;

	@Override
    public  void setApplicationContext(ApplicationContext applicationContext) throws BeansException{
        this.applicationContext = applicationContext;
    }

    public static <T> T  getBean(Class<T> clazz){
        return applicationContext.getBean(clazz);
    }

    public  static <T> T getBean(String name){
        return (T)applicationContext.getBean(name);
    }
    
    public static <T> List<T> getBeans(Class<T> clazz) {
    	Map<String, T> map = applicationContext.getBeansOfType(clazz);
    	if(map != null) {
    		return new ArrayList<>( map.values());
    	}
    	return Lists.newArrayList();
    }
    
    public static void publishEvent(ApplicationEvent event) {
    	applicationContext.publishEvent(event);
    }
    
    public static void publishEvent(Object object) {
    	applicationContext.publishEvent(object);
    }

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}


}
