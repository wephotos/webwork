package com.github.wephotos.webwork.utils;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * 工具类
 * @author TQ
 *
 */
public class WebworkUtils {

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
	public static Timestamp timestamp() {
		return new Timestamp(System.currentTimeMillis());
	}
}
