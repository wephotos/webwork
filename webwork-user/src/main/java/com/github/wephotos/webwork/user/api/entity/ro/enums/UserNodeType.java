package com.github.wephotos.webwork.user.api.entity.ro.enums;

import lombok.Getter;

/**
 * 用户树节点类型
 * @author TQ
 *
 */
@Getter
public enum UserNodeType {
	/**
	 * 虚拟节点
	 */
	VIRTUAL(0, "虚拟节点"),
	/**
	 * 单位
	 */
	GROUP(1, "单位"),
	/**
	 * 部门
	 */
	DEPT(2, "部门"),
	/**
	 * 用户
	 */
	USER(3, "用户");

	UserNodeType(int type, String name) {
		this.type = type;
		this.name = name;
	}

	/**
	 * 节点类型
	 */
	private int type;
	/**
	 * 类型名称
	 */
	private String name;
	
}
