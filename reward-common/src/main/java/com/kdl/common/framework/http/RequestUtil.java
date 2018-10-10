package com.kdl.common.framework.http;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;



public class RequestUtil {
	
	public static final String SELF_IP= "127.0.0.1";
	public static final String UNKNOW_IP = "unkonw ip";
	
	public static String getRequestUri() {
    	return getRequestUri(null);
	}
	public static String getRequestUri(HttpServletRequest request) {
		if(request == null) {
			request = getRequest();
		}
		String contextPath = getContextPath(request);
    	String requestUri = request.getRequestURI();
    	return requestUri.replaceFirst(contextPath, "");
	}
	
	public static String getContextPath() {
		return getContextPath(null);
	}
	public static String getContextPath(HttpServletRequest request) {
		if(request == null) {
			request = getRequest();
		}
		return request.getContextPath();
	}
	
	public static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	public static HttpSession getSession(HttpServletRequest request) {
		if(request == null) {
			request = getRequest();
		}
		return request.getSession();
	}
	
	public static HttpSession getSession() {
		return getSession(null);
	}
	
	public static HttpHeaders getHeaders() {
		HttpServletRequest request = getRequest();
		return getHeaders(request);
	}
	
	public static HttpHeaders getHeaders(HttpServletRequest request) {
		if(request == null) {
			request = getRequest();
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setIp(getIp(request));
		Enumeration headerNames = request.getHeaderNames();
	    while (headerNames.hasMoreElements()) {
	        String key = (String) headerNames.nextElement();
	        String value = request.getHeader(key);
	        headers.add(key, value);
	    }
	    
		return headers;
	}
	
	public static String getIP() {
		HttpServletRequest request = getRequest(); 
        return getIp(request);
	}
	
	/**
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
     * 
     */
    public static String getIp(HttpServletRequest request)  {
        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++) {
                String strIp = (String) ips[index];
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }
        return ip;
    }

}
