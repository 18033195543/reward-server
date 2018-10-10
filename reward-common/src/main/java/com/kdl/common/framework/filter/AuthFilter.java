package com.kdl.common.framework.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthFilter implements Filter {
	private Logger logger = LoggerFactory.getLogger(AuthFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.debug(this.getClass() + "started .....");

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//TODO 从requet 里获取token，　然后根据token 从Redis里获取对应的用户ID，最后放入到UserContextUtil里
		chain.doFilter(new DefaultRequestWrapper((HttpServletRequest)request), response);
	}

	@Override
	public void destroy() {

	}

}
