package com.github.wephotos.webwork.core.entity;

/**
 * 角色树节点类型
 * @author TQ
 *
 */
public enum RoleNodeType {
	ORG(0, "单位"),
	APP(1, "应用"),
	ROLE(2, "角色");

	/**
	 * 枚举构造
	 * @param type 类型
	 * @param name 名称
	 */
	RoleNodeType(int type, String name) {
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
