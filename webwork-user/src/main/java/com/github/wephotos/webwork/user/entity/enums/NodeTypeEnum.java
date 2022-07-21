package com.github.wephotos.webwork.user.entity.enums;

import lombok.Getter;

/**
 * 节点类型定义
 * 0  - 10: 单位、用户节点类型
 * 10 - 20: 角色节点类型
 * 20 - 30: 资源节点类型
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
	
	// 节点代码
	private int code;
	
	// 节点类型名称
	private String name;
	
	/**
	 * 判断类型代码是否匹配
	 * @param code 类型代码
	 * @return {@link Boolean}
	 */
	public boolean is(Integer code) {
		return code != null && code.equals(this.code);
	}
}
