package com.github.wephotos.webwork.core.entity;

/**
 * 用户树节点类型
 * @author TQ
 *
 */
public enum UserNodeType {
	VIRTUAL(0, "虚拟节点"),
	GROUP(1, "单位"),
	DEPT(2, "部门"),
	USER(3, "用户");
	
	UserNodeType(int type, String name){
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