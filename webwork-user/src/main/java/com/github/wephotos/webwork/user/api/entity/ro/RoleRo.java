package com.github.wephotos.webwork.user.api.entity.ro;

import com.github.wephotos.webwork.user.entity.Role;

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
public class RoleRo extends Role {

	/**
	 * 所属应用
	 */
	private String app;
	
}
