package com.github.wephotos.webwork.core.entity;

/**
 * 组织节点类型
 * @author TQ
 *
 */
public enum OrgType {
	// 虚拟节点
	VIRTUAL(0, "虚拟节点"),
	// 单位
	GROUP(1, "单位"),
	// 部门
	DEPT(2, "部门");
	
	OrgType(int type, String name){
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
	
	public int getType() {
		return type;
	}
	public String getName() {
		return name;
	}
}
