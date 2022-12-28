package com.github.wephotos.webwork.utils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

/**
 * 工具类
 * @author TQ
 *
 */
public final class WebworkUtils {

	private WebworkUtils() {
		
	}
	
	/**
	 * 获取32位UUID字符串
	 * @return UUID
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	/**
	 * 获取当前时间戳
	 * @return 当前时间
	 */
	public static Date nowTime() {
		return new Timestamp(System.currentTimeMillis());
	}
}
