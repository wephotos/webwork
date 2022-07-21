package com.github.wephotos.webwork.user.api.enums;

import lombok.Getter;

/**
 * 树节点类型枚举
 * @author TianQi
 *
 */
@Getter
public enum NodeTypeEnum {
	
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
	USER(3, "用户"),
	
	/**
	 * 角色
	 */
	ROLE(10, "角色"),

	/**
	 * 应用
	 */
	APP(20, "应用"),
	/**
	 * 模块
	 */
	MODULE(21, "模块"),
	/**
	 * 功能
	 */
	FUNCTION(22, "功能");

	/**
	 * 节点类型构造函数
	 */
	NodeTypeEnum(int code, String name){
		this.code = code;
		this.name = name;
	}
	
	private int code;
	
	private String name;
}
