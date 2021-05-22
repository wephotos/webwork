package com.github.wephotos.webwork.core.entity;

/**
 * 资源类型
 * @author TQ
 *
 */
public enum ResourceType {
	ORG(0, "单位"),
	APP(1, "应用"),
	MODULE(2, "模块"),
	FUNCTION(3, "功能");

	/**
	 * 枚举构造
	 * @param type 类型
	 * @param name 名称
	 */
	ResourceType(int type, String name) {
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

	public int getType() {
		return type;
	}

	public String getName() {
		return name;
	}
}
