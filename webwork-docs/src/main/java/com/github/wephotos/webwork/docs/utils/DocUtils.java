package com.github.wephotos.webwork.docs.utils;

import com.github.wephotos.webwork.docs.entity.ContentType;

/**
 * 文档工具类
 * @author TianQi
 *
 */
public final class DocUtils {

	/**
	 * 解析文档内容为纯文本
	 * @param content 文档内容
	 * @param contentType 文档类型
	 * @return 纯文本内容
	 */
	public static String getPlainText(String content, ContentType contentType) {
		return ContentParserFactory.getContentParser(contentType).getPlainText(content);
	}
}
