package com.github.wephotos.webwork.boot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.github.wephotos.webwork.logging.LoggerRequestHandler;
import com.github.wephotos.webwork.security.entity.SecurityUser;
import com.github.wephotos.webwork.security.utils.SecurityUtils;

/**
 * 服务启动后执行
 * @author TQ
 *
 */
@Component
public class WebworkBootRunner implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		// 注入获取日志操作者方法
		LoggerRequestHandler.setLogUserContext((session) -> {
			SecurityUser user = SecurityUtils.getSecurityUser(session);
			return user == null ? "" : user.getName();
		});
	}

}
