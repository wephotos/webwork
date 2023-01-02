package com.github.wephotos.webwork.docs.entity;

import lombok.Getter;

/**
 * 用户对文档的访问类型
 * @author TianQi
 *
 */
@Getter
public enum UserAccessType {

	CREATOR(0, "创建者"), EDIT(1, "编辑"), READ(2, "查阅");
	
	/**
	 * 枚举构建函数
	 * @param code 代码
	 * @param name 名称
	 */
	UserAccessType(int code, String name){
		this.code = code;
		this.name = name;
	}
	
	private int code;
	private String name;
	
	public boolean equalsCode(Integer code) {
		return code != null && code.equals(this.code);
	}
}
