package com.github.wephotos.webwork.core.entity.vo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.wephotos.webwork.core.entity.Dictionary;
import com.github.wephotos.webwork.core.entity.Organization;
import com.github.wephotos.webwork.core.entity.Resource;
import com.github.wephotos.webwork.core.entity.Role;
import com.github.wephotos.webwork.core.entity.RoleNodeType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 树节点
 * @author TQ
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class TreeNode {
	/**
	 * 标识
	 */
	private String id;
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
	 * 父节点
	 */
	private String parentId;
	/**
	 * 子节点集合
	 */
	private List<TreeNode> children = Collections.emptyList();
	/**
	 * 扩展属性
	 */
	private Map<String, String> extAttrs = new HashMap<>();
	
	/**
	 * 使用组织架构对象构建树节点
	 * @param org 组织架构对象
	 */
	public TreeNode(Organization org) {
		setId(org.getId());
		setName(org.getName());
		setCode(org.getCode());
		setType(org.getType());
		setParentId(org.getParentId());
	}
	
	/**
	 * 使用组织架构对象构建树节点
	 * @param org 组织架构对象
	 */
	public TreeNode(Resource res) {
		setId(res.getId());
		setName(res.getName());
		setCode(res.getCode());
		setType(res.getType());
		setParentId(res.getParentId());
	}
	
	/**
	 * 使用组织架构对象构建树节点
	 * @param org 组织架构对象
	 */
	public TreeNode(Role role) {
		setId(role.getId());
		setName(role.getName());
		setCode(role.getCode());
		setType(RoleNodeType.ROLE.getType());
		setParentId(role.getParentId());
	}
	
	/**
	 * 构建数据字典树节点
	 * @param dict 数据字典
	 */
	public TreeNode(Dictionary dict) {
		setId(dict.getId());
		setName(dict.getName());
		setCode(dict.getCode());
		setParentId(dict.getParentId());
	}
	
	/**
	 * 组织架构对象转换为树节点
	 * @param org 组织架构对象
	 * @return 树节点
	 */
	public static TreeNode from(Organization org) {
		return new TreeNode(org);
	}
	
	/**
	 * 添加子节点
	 * @param child 子节点
	 */
	public void addChild(TreeNode child) {
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
