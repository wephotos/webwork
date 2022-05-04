package com.github.wephotos.webwork.user.entity.enums;

import lombok.Getter;

/**
 * 资源树节点类型
 * @author TQ
 *
 */
@Getter
public enum ResNodeType {
	/**
	 * 单位
	 */
	GROUP(0, "单位"),
	/**
	 * 应用
	 */
	APP(1, "应用"),
	/**
	 * 模块
	 */
	MODULE(2, "模块"),
	/**
	 * 功能
	 */
	FUNCTION(3, "功能");
	
	/**
	 * 枚举构造
	 * @param type 类型
	 * @param name 名称
	 */
	ResNodeType(int type, String name) {
		this.type = type;
	}

	/**
	 * 节点类型
	 */
	private int type;
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 判断类型是否匹配
	 * @param type 类型
	 * @return {@link Boolean}
	 */
	public boolean is(Integer type) {
		return type != null && type.equals(this.type);
	}
}
