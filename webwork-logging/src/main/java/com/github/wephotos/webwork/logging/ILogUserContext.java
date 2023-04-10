package com.github.wephotos.webwork.logging;

import javax.servlet.http.HttpSession;

/**
 * 日志上下文用户接口
 * 
 * @author TQ
 *
 */
public interface ILogUserContext {

	/**
	 * 获取当前用户名
	 * 
	 * @param session HttpSession
	 * @return 用户名
	 */
	String getUsername(HttpSession session);
}
