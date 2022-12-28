package com.github.wephotos.webwork.user.client.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.wephotos.webwork.user.client.UserClient;
import com.github.wephotos.webwork.user.client.UserClientHttp;

/**
 * 配置类，用于实例化<UserClient>对象
 * @author TianQi
 *
 */
@Configuration
public class UserClientConfiguration {

	@Bean
	@ConditionalOnMissingClass("com.github.wephotos.webwork.user.service.client.UserClientDefault")
	public UserClient userClient() {
		return new UserClientHttp();
	}
}
