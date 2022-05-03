package com.github.wephotos.webwork.security.entity;

import java.util.Collections;
import java.util.Set;

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
public class SecurityUser {

	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 部门ID
	 */
	private Integer deptId;
	/**
	 * 部门名称
	 */
	private String deptName;
	/**
	 * 单位ID
	 */
	private Integer groupId;
	/**
	 * 单位名称
	 */
	private String groupName;
	
	/**
	 * 权限代码集合
	 */
	private Set<String> permissions = Collections.emptySet();
	
}
