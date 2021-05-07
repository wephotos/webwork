package com.github.wephotos.webwork.logging;

import org.slf4j.helpers.Util;

/**
 * 工具类
 * @author TQ
 *
 */
public final class LoggerUtils {

	/**
	 * 标识WEB环境的类名
	 */
	public static final String WEB_CLASS_NAME = "javax.servlet.Servlet";
	// 是否web环境
	private static volatile Boolean WEB_ENV;
	
	/**
	 * 判断当前环境是否是WEB环境
	 * @return
	 */
	public static boolean isWebEnv() {
		if(WEB_ENV != null) {
			return WEB_ENV;
		}
		try {
			return (WEB_ENV = null != Class.forName(WEB_CLASS_NAME));
		} catch (ClassNotFoundException e) {
			Util.report(e.getMessage());
			return (WEB_ENV = false);
		}
	}
}
