package com.github.wephotos.webwork.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

import com.github.wephotos.webwork.security.auth.SecurityAuth;
import com.github.wephotos.webwork.security.filter.SecurityAuthFilter;
import com.github.wephotos.webwork.security.service.SecurityService;

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
	 * 初始化认证服务
	 * @param securityAuth 认证接口
	 * @return 认证服务对象
	 */
	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnBean(value=SecurityAuth.class)
	public SecurityService securityService(SecurityAuth securityAuth) {
		SecurityService securityService = new SecurityService();
		securityService.setSecurityAuth(securityAuth);
		return securityService;
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
		registration.setFilter(new SecurityAuthFilter(props.getLoginUrl(), props.getIgnorePaths()));
		return registration;
	}
}
