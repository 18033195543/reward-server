package com.cjf.java.filter;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.cjf.java.api.dto.Accordion;
import com.cjf.java.context.AccountContext;
import com.cjf.java.context.LoginAccountCache;
import com.cjf.java.context.ResponseContext;
import com.cjf.java.entity.AccountEntity;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AccountContextFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		ResponseContext.setCurrent(response);

		if (request.getRequestURI().contains("/login/loginPage")) {
			chain.doFilter(request, response);
			return;
		}

		if (request.getRequestURI().contains("/login/auth")) {
			Cookie cookie = new Cookie("auth", null);
			cookie.setMaxAge(0);
			cookie.setPath("/");
			response.addCookie(cookie);
			chain.doFilter(request, response);
			return;
		}

		if (request.getRequestURI().endsWith(".css") || request.getRequestURI().endsWith(".js")
				|| request.getRequestURI().endsWith(".ico") || request.getRequestURI().endsWith(".jpg")
				|| request.getRequestURI().endsWith(".gif") || request.getRequestURI().endsWith(".png")
				|| request.getRequestURI().endsWith(".map")) {
			chain.doFilter(request, response);
			return;
		}

		String cookieValue = "";
		if (null != request.getCookies()) {
			for (Cookie cookie : request.getCookies()) {
				if (Objects.equals("auth", cookie.getName())) {
					cookieValue = cookie.getValue();
					break;
				}
			}
		}

		byte[] decode = Base64.getDecoder().decode(cookieValue);

		String auth = new String(decode);
		String[] split = auth.split("\\$");
		if (2 == split.length) {
			AccountEntity account = LoginAccountCache.get(split[0]);
			if (null == account) {
				// WebApplicationContext.getBean();
				account = LoginAccountCache.get(split[0]);
			}
			if (null != account && Objects.equals(account.getPassword(), split[1])) {
				List<Accordion> list = LoginAccountCache.getAccordions(account.getAccountName());
				request.setAttribute("accordions", list);
				AccountContext.setCurrent(account);
				LoginAccountCache.setCookie(account);
				chain.doFilter(request, response);
				return;
			} else {
				Cookie cookie = new Cookie("auth", null);
				cookie.setMaxAge(0);
				response.addCookie(cookie);
				response.sendRedirect("/login/loginPage");
			}
		} else { // 如果从cookic里拿出来的认证为空则直接跳转到登录页面
			Cookie cookie = new Cookie("auth", null);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			response.sendRedirect("/login/loginPage");
		}
		 chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}
