package com.github.wephotos.webwork.user.entity.po;

import java.io.Serializable;

import com.github.wephotos.webwork.user.entity.enums.NodeTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 资源查询
 * @author TQ
 *
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResourceQueryPO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 编码
	 */
	private String code;
	/**
	 * 类型
	 */
	private Integer type;
	/**
	 * 父ID
	 */
	private Integer parentId;
	
	/**
	 * 父节点类型
	 * {@link NodeTypeEnum}
	 */
	private Integer parentType;
	
	/**
	 * 资源唯一代码
	 */
	private String permission;
	
}
