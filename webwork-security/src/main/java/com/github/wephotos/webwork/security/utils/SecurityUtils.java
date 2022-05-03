package com.github.wephotos.webwork.security.utils;

import javax.servlet.http.HttpSession;

import com.github.wephotos.webwork.security.entity.SecurityUser;

/**
 * 会话用户存储工具类
 * @author TQ
 *
 */
public final class SecurityUtils {
	
	/**
	 * 会话用户
	 */
	public static final String USER_KEY = "webwork:session:user";

	//禁止实例
	private SecurityUtils() {
		throw new IllegalStateException("access denied");
	}
	
	/**
	 * 设置会话用户
	 * @param user 用户
	 * @param session 会话
	 */
	public static void setSecurityUser(SecurityUser user, HttpSession session) {
		session.setAttribute(USER_KEY, user);
	}
	
	/**
	 * 获取会话用户
	 * @param session 会话
	 * @return 用户
	 */
	public static SecurityUser getSecurityUser(HttpSession session) {
		Object user = session.getAttribute(USER_KEY);
		if(user instanceof SecurityUser) {
			return (SecurityUser)user;
		}else {
			return null;
		}
	}
}
