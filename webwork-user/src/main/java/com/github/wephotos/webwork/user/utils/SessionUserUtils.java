package com.github.wephotos.webwork.user.utils;

import javax.servlet.http.HttpSession;

import com.github.wephotos.webwork.security.entity.SecurityUser;

/**
 * 会话用户存储工具类
 * @author TQ
 *
 */
public final class SessionUserUtils {
	
	/**
	 * 会话用户
	 */
	public static final String USER_KEY = "webwork:session:user";

	//禁止实例
	private SessionUserUtils() {
		
	}
	
	/**
	 * 获取会话用户
	 * @param session 会话
	 * @return 用户
	 */
	public static SecurityUser getUser(HttpSession session) {
		if(session == null) {
			return null;
		}
		Object user = session.getAttribute(USER_KEY);
		if(user instanceof SecurityUser) {
			return (SecurityUser)user;
		}else {
			return null;
		}
	}
}
