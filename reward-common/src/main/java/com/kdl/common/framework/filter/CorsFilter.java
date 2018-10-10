package com.kdl.common.framework.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;



public class CorsFilter implements Filter{
	protected Logger logger = LoggerFactory.getLogger(CorsFilter.class);
	
	@Value("${cors.switch:false}")
	private boolean cors;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if(cors) {
			logger.warn("-------- cors is turn on ----------");
			HttpServletResponse servletResponse = (HttpServletResponse) response;
			HttpServletRequest servletRequest = (HttpServletRequest) request;
			String origin = servletRequest.getHeader("Origin");
			if (StringUtils.isNotEmpty(origin)) {
				//允许客户端携带跨域cookie，此时origin值不能为“*”，只能为指定单一域名
				servletResponse.setHeader("Access-Control-Allow-Origin", origin);
			} else {
				servletResponse.setHeader("Access-Control-Allow-Origin", "*");
			}
			servletResponse.setHeader("Access-Control-Allow-Methods", "*");
			servletResponse.setHeader("Access-Control-Allow-Headers", "*,Content-Type");
			servletResponse.setHeader("Access-Control-Expose-Headers", "*");
			servletResponse.setHeader("Access-Control-Max-Age", "3600");
			servletResponse.setHeader("Access-Control-Allow-Credentials", "true");
		}
		chain.doFilter(request, response);
		
	}

	@Override
	public void destroy() {
		
	}

}
