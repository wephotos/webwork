package com.github.wephotos.webwork.core.entity;

/**
 * 组织节点类型
 * @author TQ
 *
 */
public enum OrgType {
	// 虚拟节点
	VIRTUAL(0),
	// 单位
	GROUP(1),
	// 部门
	DEPT(2);
	
	OrgType(int type){
		this.type = type;
	}
	
	/**
	 * 节点类型
	 */
	private int type;
	
	public int getType() {
		return type;
	}
}
