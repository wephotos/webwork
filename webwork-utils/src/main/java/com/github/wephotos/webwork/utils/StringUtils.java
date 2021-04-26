package com.github.wephotos.webwork.utils;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 字符串工具类
 *
 * @author TQ
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

	/**
	 * 连接集合属性
	 * @param elements 集合
	 * @param mapper 转换函数
	 * @param delimiter 分隔符
	 * @return 连接字符串
	 */
	public static <E> String join(Collection<E> elements, Function<E, String> mapper, String delimiter) {
		return elements.stream().map(mapper).collect(Collectors.joining(delimiter));
	}
}
