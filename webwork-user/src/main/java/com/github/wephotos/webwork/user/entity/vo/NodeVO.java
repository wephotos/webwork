package com.github.wephotos.webwork.user.entity.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.wephotos.webwork.user.entity.enums.NodeTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 树节点数据定义
 * @author TQ
 *
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NodeVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 树节点ID，节点ID由以下二个属性组成，以下划线连接
	 * {@link #type}_{@link #rawId}
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
	 * 排序号
	 */
	private Integer sort;
	/**
	 * 父节点ID
	 */
	private String parentId;
	
	/**
	 * 子节点集合
	 */
	@Builder.Default
	private List<NodeVO> children = Collections.emptyList();
	/**
	 * 扩展属性
	 */
	@Builder.Default
	private Map<String, String> extAttrs = new HashMap<>();
	
	/**
	 * 连接节点ID
	 * @param type 节点类型 {@link NodeTypeEnum}
	 * @param rawId 原始ID
	 * @return 树节点ID
	 */
	public static String joinId(Integer type, Object rawId) {
		if(rawId == null) {
			return null;
		}
		if(type == null) {
			return rawId.toString();
		}
		return String.format("%s_%s", type, rawId);
	}
	
	/**
	 * 获取节点ID，如果不存在，自动使用类型和原始ID拼接
	 * @return 节点ID
	 */
	public String getId() {
		if(this.id != null) {
			return this.id;
		}else {
			return joinId(type, rawId);
		}
	}
	
	/**
	 * 添加子节点
	 * @param child 子节点
	 */
	public NodeVO addChild(NodeVO child) {
		if(this.children == Collections.EMPTY_LIST) {
			this.children = new ArrayList<>();
		}
		this.children.add(child);
		return this;
	}
	
	/**
	 * 扩展属性
	 * @param key
	 * @param value
	 * @return
	 */
	public NodeVO addExtAttr(String key, String value) {
		extAttrs.put(key, value);
		return this;
	}
}
