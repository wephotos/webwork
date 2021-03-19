package com.github.wephotos.logging;

import org.slf4j.Logger;

/**
 * 日志工厂类
 * @author TQ
 *
 */
public final class LoggerFactory {

	/**
	 * 根据类名获取日志实现
	 * @param clazz 日志类
	 * @return 日志对象
	 */
	public static Logger getLogger(Class<?> clazz) {
		Logger logger = org.slf4j.LoggerFactory.getLogger(clazz);
		return new WebworkLogger(logger);
	}
	
	/**
	 * 根据指定名称获取日志实现
	 * @param name 日志名
	 * @return 日志对象
	 */
	public static Logger getLogger(String name) {
		Logger logger = org.slf4j.LoggerFactory.getLogger(name);
		return new WebworkLogger(logger);
	}
}