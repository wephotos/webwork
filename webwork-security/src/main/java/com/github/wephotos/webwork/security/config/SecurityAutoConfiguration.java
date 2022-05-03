package com.github.wephotos.webwork.security.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import com.github.wephotos.webwork.security.filter.SecurityAuthFilter;

/**
 * 配置类
 * Com.Github0Wephotos
 * @author TQ
 *
 */
@Configuration
@EnableConfigurationProperties(value = SecurityProperties.class)
public class SecurityAutoConfiguration {

	//配置文件
	private final SecurityProperties props;

    public SecurityAutoConfiguration(SecurityProperties props) {
        this.props = props;
    }
	
	/**
	 * 初始化权限过滤器
	 * @return 过滤器注册对象
	 */
	@Bean
	@ConditionalOnMissingBean
	public FilterRegistrationBean<SecurityAuthFilter> securityAuthFilter() {
		FilterRegistrationBean<SecurityAuthFilter> registration = new FilterRegistrationBean<>();
		registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
		registration.addUrlPatterns(props.getUrlPatterns());
		registration.setFilter(new SecurityAuthFilter(props.getLoginUrl(), props.getIgnorePatterns()));
		return registration;
	}
}
