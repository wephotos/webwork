package com.github.wephotos.webwork.user.entity.po;

import java.io.Serializable;

import com.github.wephotos.webwork.user.entity.enums.NodeTypeEnum;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 树节点查询参数
 * @author TianQi
 *
 */
@Getter
@Setter
@ToString
public class NodeQueryPO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 父节点ID
	 */
	private String parentId;
	
	/**
	 * 父节点类型
	 * @see {@link NodeTypeEnum}
	 */
	private Integer parentType;
	
	/**
	 * 用户单位ID
	 */
	private Integer userGroupId;
}
