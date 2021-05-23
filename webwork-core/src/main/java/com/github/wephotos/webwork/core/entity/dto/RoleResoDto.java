package com.github.wephotos.webwork.core.entity.dto;

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
public class RoleResoDto implements Serializable {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 角色ID
	 */
	private String roleId;
	/**
	 * 资源集合
	 */
	private List<String> resources = Collections.emptyList();
}
