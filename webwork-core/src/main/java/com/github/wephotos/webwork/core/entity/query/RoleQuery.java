package com.github.wephotos.webwork.core.entity.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Setter;

/**
 * 角色查询
 * @author TQ
 *
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RoleQuery {

	/**
	 * 角色名称
	 */
	private String name;
	/**
	 * 父ID
	 */
	private String parentId;
}
