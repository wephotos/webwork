package com.github.wephotos.webwork.user.api.entity.ro.enums;

import lombok.Getter;

/**
 * 角色树节点类型
 * @author TQ
 *
 */
@Getter
public enum RoleNodeType {
	/**
	 * 单位
	 */
	GROUP(0, "单位"),
	/**
	 * 应用
	 */
	APP(1, "应用"),
	/**
	 * 角色
	 */
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
	
}
