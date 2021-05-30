package com.github.wephotos.webwork.core.spring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.github.wephotos.webwork.logging.LoggerRequestHandler;
import com.github.wephotos.webwork.security.entity.User;
import com.github.wephotos.webwork.security.storage.SessionUserStorage;

/**
 * 服务启动后执行
 * @author TQ
 *
 */
@Component
public class WebworkCoreRunner implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		// 注入获取日志操作者方法
		LoggerRequestHandler.setOperator((session) -> {
			User user = SessionUserStorage.get(session);
			return user == null ? "未知" : user.getName();
		});
	}

}
