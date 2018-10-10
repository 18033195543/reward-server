package com.cjf.java.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.alibaba.fastjson.JSON;
import com.cjf.java.entity.UserEntity;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AuthFilter extends OncePerRequestFilter{

	private static String [] EXCLUDES = {
			"/user/userRegister",
			"/user/userLogin"
			};
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {


		if (!shouldNotAuth(request)) {
			Map<String,Object> map = new HashMap<>();
			HttpSession session = request.getSession(true);
			UserEntity user = (UserEntity) session.getAttribute("user");
			
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			if (user == null) {
				log.warn("request auth key is null, sessionId: {}", session.getId());
				PrintWriter writer = response.getWriter();
				map.put("code", 10000);
				map.put("msg", new String("未登录".getBytes(),"utf-8"));
				writer.write(JSON.toJSONString(map));
				return;
			}
		}
		filterChain.doFilter(request, response);
	}

	private boolean shouldNotAuth(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        for (String exclude : EXCLUDES) {
            if(requestUri.contains(exclude)) {
                return true;
            }
        }
        return false;
    }
}
