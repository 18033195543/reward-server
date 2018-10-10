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
import org.springframework.util.StopWatch;

import com.kdl.common.util.jdkUtil.NDCUtils;

public class TraceFilter implements Filter {
	
	private Logger logger = LoggerFactory.getLogger(TraceFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.debug(this.getClass() + "started .....");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		NDCUtils.push();
		StopWatch sw = new StopWatch();
		sw.start();
		chain.doFilter(request, response);
		sw.stop();
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		String requestURI = httpServletRequest.getRequestURI();
		logger.info("request: 【{}】  comleted use {} ms",requestURI,sw.getTotalTimeMillis());
		NDCUtils.clear();

	}

	@Override
	public void destroy() {

	}

}
