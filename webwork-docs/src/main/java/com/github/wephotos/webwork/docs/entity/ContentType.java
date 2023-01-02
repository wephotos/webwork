package com.github.wephotos.webwork.docs.entity;

import lombok.Getter;

/**
 * 文档内容类型枚举
 * @author TianQi
 *
 */
@Getter
public enum ContentType {

	PLAIN_TEXT(0, "纯文本"), MARKDOWN(1, "markdown"), RICH_TEXT(2, "富文本");
	
	/**
	 * 枚举构建函数
	 * @param code 代码
	 * @param name 名称
	 */
	ContentType(int code, String name){
		this.code = code;
		this.name = name;
	}
	
	private Integer code;
	private String name;
	
	/**
	 * 解析内容类型代码
	 * @param code 类型代码
	 * @return 内容类型枚举
	 */
	public static ContentType valueOf(Integer code) {
		for(ContentType item : ContentType.values()) {
			if(item.getCode().equals(code)) {
				return item;
			}
		}
		return null;
	}
}
