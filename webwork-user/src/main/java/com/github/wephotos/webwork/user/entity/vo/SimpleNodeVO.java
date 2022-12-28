package com.github.wephotos.webwork.user.entity.vo;

import java.io.Serializable;

import com.github.wephotos.webwork.user.entity.enums.NodeTypeEnum;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 简单的节点类型数据
 * 包含节点ID和节点类型信息
 * @author TianQi
 *
 */
@Getter
@Setter
@ToString
public class SimpleNodeVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 节点ID
	 */
	private Integer id;
	
	/**
	 * 节点类型
	 * @see
	 * {@link NodeTypeEnum}
	 */
	private Integer type;
}
