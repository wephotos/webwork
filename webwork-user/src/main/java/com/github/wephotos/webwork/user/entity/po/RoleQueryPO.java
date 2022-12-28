package com.github.wephotos.webwork.user.entity.po;

import java.io.Serializable;

import com.github.wephotos.webwork.user.entity.enums.NodeTypeEnum;

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
public class RoleQueryPO implements Serializable {

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
	
	/**
	 * 父节点类型
	 * {@link NodeTypeEnum}
	 */
	private Integer parentType;
}
