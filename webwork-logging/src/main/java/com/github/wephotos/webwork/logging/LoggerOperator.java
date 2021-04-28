package com.github.wephotos.webwork.logging;

import javax.servlet.http.HttpSession;

/**
 * 获取日志操作人
 * @author TQ
 *
 */
public interface LoggerOperator {

	/**
	 * 获取当前操作者
	 * @param session 会话
	 * @return 操作人
	 */
	String getName(HttpSession session);
}
