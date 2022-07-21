package com.github.wephotos.webwork.user.api.entity.ro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.wephotos.webwork.user.api.enums.NodeTypeEnum;

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
public class TreeNodeRo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 标识
	 */
	private Integer id;
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
	 * 父节点
	 */
	private Integer parentId;
	/**
	 * 子节点集合
	 */
	@Builder.Default
	private List<TreeNodeRo> children = Collections.emptyList();
	/**
	 * 扩展属性
	 */
	@Builder.Default
	private Map<String, String> extAttrs = new HashMap<>();
	
	/**
	 * 添加子节点
	 * @param child 子节点
	 */
	public void addChild(TreeNodeRo child) {
		if(this.children == Collections.EMPTY_LIST) {
			this.children = new ArrayList<>();
		}
		this.children.add(child);
	}
	
	/**
	 * 扩展属性
	 * @param key
	 * @param value
	 * @return
	 */
	public String addExtAttr(String key, String value) {
		return extAttrs.put(key, value);
	}
}
