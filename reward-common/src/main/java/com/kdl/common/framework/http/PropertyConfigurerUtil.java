package com.kdl.common.framework.http;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 获取Spring容器里 properties 配置文件
 */
public class PropertyConfigurerUtil extends PropertyPlaceholderConfigurer {
	
	 private static Properties properties;    // 存取properties配置文件key-value结果
	 
	  @Override
	  protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
	              throws BeansException {
	    super.processProperties(beanFactoryToProcess, props);
	    properties = props;
	  }
	 
	  public static  String getProperty(String key){
	    return properties.getProperty(key);
	  }
	 
	  public static  String getProperty(String key, String defaultValue) {
	    return properties.getProperty(key, defaultValue);
	  }
	 
	  public static  Object setProperty(String key, String value) {
	    return properties.setProperty(key, value);
	  }
}
