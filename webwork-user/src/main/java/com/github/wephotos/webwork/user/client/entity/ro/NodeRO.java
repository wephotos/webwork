package com.github.wephotos.webwork.user.client.entity.ro;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.github.wephotos.webwork.user.entity.enums.NodeTypeEnum;
import com.github.wephotos.webwork.user.entity.vo.NodeVO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 节点信息，一般用于展示树节点。
 * 节点包括：单位、部门、角色、用户、应用、模块、功能、数据字典等数据
 * @author TianQi
 *
 */
@Getter
@Setter
@ToString
public class NodeRO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 节点ID
	 * 结构: type_rawId
	 */
	private String id;
	
	/**
	 * 原始数据ID
	 */
	private Integer rawId;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 编码
	 */
	private String code;
	/**
	 * 节点类型
	 * @see {@link NodeTypeEnum}
	 */
	private Integer type;
	/**
	 * 父节点ID
	 */
	private String parentId;
	/**
	 * 子节点集合
	 */
	private List<NodeVO> children = Collections.emptyList();
	/**
	 * 扩展属性
	 */
	private Map<String, String> extAttrs = Collections.emptyMap();
}
