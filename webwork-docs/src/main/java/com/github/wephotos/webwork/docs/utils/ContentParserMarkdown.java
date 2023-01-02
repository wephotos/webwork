package com.github.wephotos.webwork.docs.utils;

import com.github.wephotos.webwork.utils.StringUtils;

/**
 * Markdown文档解析
 * @author TianQi
 *
 */
public class ContentParserMarkdown implements ContentParser {

	@Override
	public String getPlainText(String content) {
		if(StringUtils.isBlank(content)) {
			return "";
		}
		return content.replaceAll("[#=`~*+\\-\n\r|]+", "");
	}

}
