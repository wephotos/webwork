package com.github.wephotos.webwork.user.entity.po;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 角色资源接收数据
 * @author TQ
 *
 */
@Getter
@Setter
@ToString
public class RoleResourcesPO implements Serializable {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 角色ID
	 */
	private Integer roleId;
	/**
	 * 资源集合
	 */
	private List<Integer> resources = Collections.emptyList();
	
	/**
	 * 创建用户
	 */
	private String createUser;
}
