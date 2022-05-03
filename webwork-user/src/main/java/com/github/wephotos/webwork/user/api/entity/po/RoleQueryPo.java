package com.github.wephotos.webwork.user.api.entity.po;

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
public class RoleQueryPo {

	/**
	 * 角色名称
	 */
	private String name;
	/**
	 * 父ID
	 */
	private Integer parentId;
}
