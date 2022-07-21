package com.github.wephotos.webwork.user.utils;

import com.github.wephotos.webwork.user.api.entity.ro.TreeNodeRo;
import com.github.wephotos.webwork.user.api.enums.NodeTypeEnum;
import com.github.wephotos.webwork.user.entity.Dictionary;
import com.github.wephotos.webwork.user.entity.Organization;
import com.github.wephotos.webwork.user.entity.Resource;
import com.github.wephotos.webwork.user.entity.Role;
import com.github.wephotos.webwork.user.entity.User;

/**
 * 树节点转换器
 * 
 * @author TianQi
 *
 */
public final class TreeNodeConverter {
	
	/**
	 * 使用组织架构对象构建树节点
	 * 
	 * @param org 组织架构对象
	 */
	public static TreeNodeRo from(Organization org) {
		return TreeNodeRo.builder()
				.rawId(org.getId())
				.name(org.getName())
				.code(org.getCode())
				.type(org.getType())
				.parentId(TreeNodeRo.joinId(org.getParentType(), org.getParentId())).build();
	}

	/**
	 * 构建用户树节点
	 * 
	 * @param user 用户
	 */
	public static TreeNodeRo from(User user) {
		return TreeNodeRo.builder()
				.rawId(user.getId())
				.name(user.getName())
				.type(NodeTypeEnum.USER.getCode()).build();
	}

	/**
	 * 使用组织架构对象构建树节点
	 * 
	 * @param org 组织架构对象
	 */
	public static TreeNodeRo from(Resource res) {
		return TreeNodeRo.builder()
				.rawId(res.getId())
				.name(res.getName())
				.code(res.getCode())
				.type(res.getType())
				.parentId(TreeNodeRo.joinId(res.getParentType(), res.getParentId())).build();
	}

	/**
	 * 使用组织架构对象构建树节点
	 * 
	 * @param org 组织架构对象
	 */
	public static TreeNodeRo from(Role role) {
		return TreeNodeRo.builder()
				.rawId(role.getId())
				.name(role.getName())
				.code(role.getCode())
				.type(NodeTypeEnum.ROLE.getCode())
				.parentId(TreeNodeRo.joinId(role.getParentType(), role.getParentId())).build();
	}

	/**
	 * 构建数据字典树节点
	 * 
	 * @param dict 数据字典
	 */
	public static TreeNodeRo from(Dictionary dict) {
		return TreeNodeRo.builder()
				.rawId(dict.getId())
				.name(dict.getName())
				.code(dict.getCode())
				.parentId(dict.getParentId()).build();
	}

}
