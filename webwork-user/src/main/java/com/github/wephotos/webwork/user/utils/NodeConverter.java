package com.github.wephotos.webwork.user.utils;

import com.github.wephotos.webwork.user.entity.Dictionary;
import com.github.wephotos.webwork.user.entity.Organization;
import com.github.wephotos.webwork.user.entity.Resource;
import com.github.wephotos.webwork.user.entity.Role;
import com.github.wephotos.webwork.user.entity.enums.NodeTypeEnum;
import com.github.wephotos.webwork.user.entity.vo.NodeVO;
import com.github.wephotos.webwork.user.entity.vo.UserVO;

/**
 * 树节点转换器
 * 
 * @author TianQi
 *
 */
public final class NodeConverter {
	
	/**
	 * 使用组织架构对象构建树节点
	 * 
	 * @param org 组织架构对象
	 */
	public static NodeVO from(Organization org) {
		return NodeVO.builder()
				.rawId(org.getId())
				.name(org.getName())
				.code(org.getCode())
				.type(org.getType())
				.sort(org.getSort())
				.parentId(NodeVO.joinId(org.getParentType(), org.getParentId())).build();
	}

	/**
	 * 构建用户树节点
	 * 
	 * @param user 用户
	 */
	public static NodeVO from(UserVO user) {
		return NodeVO.builder()
				.rawId(user.getId())
				.name(user.getName())
				.type(NodeTypeEnum.USER.getCode())
				.parentId(NodeVO.joinId(NodeTypeEnum.DEPT.getCode(), user.getDeptId())).build();
	}

	/**
	 * 使用组织架构对象构建树节点
	 * 
	 * @param org 组织架构对象
	 */
	public static NodeVO from(Resource res) {
		return NodeVO.builder()
				.rawId(res.getId())
				.name(res.getName())
				.code(res.getCode())
				.type(res.getType())
				.parentId(NodeVO.joinId(res.getParentType(), res.getParentId())).build();
	}

	/**
	 * 使用组织架构对象构建树节点
	 * 
	 * @param org 组织架构对象
	 */
	public static NodeVO from(Role role) {
		return NodeVO.builder()
				.rawId(role.getId())
				.name(role.getName())
				.code(role.getCode())
				.type(NodeTypeEnum.ROLE.getCode())
				.parentId(NodeVO.joinId(role.getParentType(), role.getParentId())).build();
	}

	/**
	 * 构建数据字典树节点
	 * 
	 * @param dict 数据字典
	 */
	public static NodeVO from(Dictionary dict) {
		return NodeVO.builder()
				.rawId(dict.getId())
				.name(dict.getName())
				.code(dict.getCode())
				.parentId(String.valueOf(dict.getParentId())).build();
	}

}
