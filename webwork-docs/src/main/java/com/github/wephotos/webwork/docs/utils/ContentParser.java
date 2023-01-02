package com.github.wephotos.webwork.docs.utils;

/**
 * 原文解析
 * @author TianQi
 *
 */
public interface ContentParser {

	/**
	 * 将原文解析为纯文本内容
	 * @param content 原文内容
	 * @return 纯文本内容
	 */
	String getPlainText(String content);
}
