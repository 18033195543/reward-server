package com.cjf.java.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.cjf.java.filter.CorsFilter;

@Component
public class FilterConfiguration {

	// spring boot 会按照order值的大小，从小到大的顺序来依次过滤。
		@Bean
		public FilterRegistrationBean corsFilter() {
			FilterRegistrationBean registration = new FilterRegistrationBean();
			registration.setFilter(new CorsFilter());
			registration.addUrlPatterns("/*");
			registration.addInitParameter("CorsFilter", "CorsFilter");
			registration.setName("corsFilter");
			registration.setOrder(1);
			return registration;
		}
}
