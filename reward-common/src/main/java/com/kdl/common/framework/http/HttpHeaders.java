package com.kdl.common.framework.http;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;

import com.google.common.collect.Maps;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HttpHeaders {

	private String deviceid;
	private String deviceuuid;
	private String os;
	private String channel;
	private String ip;
	private String appname;
	private String appversion;
	
	
	
	
	private Map<String,String> headers ;
	
	public HttpHeaders() {
		headers = Maps.newHashMap();
	}

	public String getHeader(String key) {
		return headers.get(key);
	}
	
	public void add(String key, String val) {
		try {
			headers.put(key, val);
			setProperty(key, val);
		} catch (Exception e) {
			if(!e.getMessage().startsWith("Method not found: ")) {
				e.printStackTrace();
			}
		}
	}

	private void setProperty(String proName, String val) throws Exception {
		PropertyDescriptor proDescriptor = new PropertyDescriptor(proName, this.getClass());
		Method methodSet = proDescriptor.getWriteMethod();
		methodSet.invoke(this, val);
	}

	private Object getProperty( String proName) throws Exception {
		PropertyDescriptor proDescriptor = new PropertyDescriptor(proName, this.getClass());
		Method methodGet = proDescriptor.getReadMethod();
		return methodGet.invoke(this);
	}

	


}
