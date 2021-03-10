package com.github.wephotos.webwork.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

import com.github.wephotos.webwork.security.auth.SecurityAuth;
import com.github.wephotos.webwork.security.service.SecurityService;

/**
 * 配置类
 * Com.Github0Wephotos
 * @author TQ
 *
 */
@Configuration
public class SecurityAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnBean(value=SecurityAuth.class)
	public SecurityService securityService(SecurityAuth securityAuth) {
		SecurityService securityService = new SecurityService();
		securityService.setSecurityAuth(securityAuth);
		return securityService;
	}
	
}
