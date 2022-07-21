package com.github.wephotos.webwork.user.api.entity.po;

import java.io.Serializable;

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
public class RoleQueryPo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 角色名称
	 */
	private String name;
	/**
	 * 父ID
	 */
	private Integer parentId;
}
