package com.github.wephotos.webwork.logging.config;

import org.springframework.boot.autoconfigure.web.servlet.ConditionalOnMissingFilterBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import com.github.wephotos.webwork.logging.MDCFilter;

/**
 * 日志配置类
 * @author TianQi
 *
 */
@Configuration
public class LogConfig {

	/**
	 * 日志MDC过滤器
	 * @return
	 */
	@Bean
	@ConditionalOnMissingFilterBean
	public FilterRegistrationBean<MDCFilter> webworkMDCFilter() {
		FilterRegistrationBean<MDCFilter> registration = new FilterRegistrationBean<>();
		registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
		registration.setFilter(new MDCFilter());
		return registration;
	}
}
