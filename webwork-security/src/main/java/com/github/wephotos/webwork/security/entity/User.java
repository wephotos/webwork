package com.github.wephotos.webwork.security.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户信息
 * @author TQ
 *
 */
@Getter
@Setter
@ToString
public class User {

	/**
	 * ID
	 */
	private String id;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 部门ID
	 */
	private String deptId;
	/**
	 * 部门名称
	 */
	private String deptName;
	/**
	 * 单位ID
	 */
	private String groupId;
	/**
	 * 单位名称
	 */
	private String groupName;
	
}
