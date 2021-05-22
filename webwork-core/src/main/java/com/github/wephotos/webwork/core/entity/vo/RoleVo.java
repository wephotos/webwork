package com.github.wephotos.webwork.core.entity.vo;

import com.github.wephotos.webwork.core.entity.Role;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 角色视图对象
 * @author TQ
 *
 */
@Getter
@Setter
@ToString
public class RoleVo extends Role {

	/**
	 * 所属应用
	 */
	private String app;
	
}
