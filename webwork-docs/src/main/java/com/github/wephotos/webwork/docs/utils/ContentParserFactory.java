package com.github.wephotos.webwork.docs.utils;

import java.util.HashMap;
import java.util.Map;

import com.github.wephotos.webwork.docs.entity.ContentType;

public final class ContentParserFactory {
	
	private static final Map<Integer, ContentParser> parserMap = new HashMap<>();
	
	static {
		parserMap.put(ContentType.MARKDOWN.getCode(), new ContentParserMarkdown());
	}

	/**
	 * 获取不到解析器时返回默认解析器不解析
	 * @param contentType 内容类型
	 * @return 解析器
	 */
	public static ContentParser getContentParser(ContentType contentType) {
		return parserMap.getOrDefault(contentType.getCode(), new ContentParser() {
			
			@Override
			public String getPlainText(String content) {
				return content;
			}
		});
	}
}
