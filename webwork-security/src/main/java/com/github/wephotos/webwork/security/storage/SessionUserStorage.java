package com.github.wephotos.webwork.security.storage;

import javax.servlet.http.HttpSession;

import com.github.wephotos.webwork.security.entity.User;

/**
 * 会话用户存储工具类
 * @author TQ
 *
 */
public final class SessionUserStorage {
	
	/**
	 * 会话用户
	 */
	public static final String USER_KEY = "webwork:session:user";

	//禁止实例
	private SessionUserStorage() {
		
	}
	
	/**
	 * 设置会话用户
	 * @param user 用户
	 * @param session 会话
	 */
	public static void put(User user, HttpSession session) {
		if(user == null || session == null) {
			return;
		}
		session.setAttribute(USER_KEY, user);
	}
	
	/**
	 * 获取会话用户
	 * @param session 会话
	 * @return 用户
	 */
	public static User get(HttpSession session) {
		if(session == null) {
			return null;
		}
		Object user = session.getAttribute(USER_KEY);
		if(user instanceof User) {
			return (User)user;
		}else {
			return null;
		}
	}
}
