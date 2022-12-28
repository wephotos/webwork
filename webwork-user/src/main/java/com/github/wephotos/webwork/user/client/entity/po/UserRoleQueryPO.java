package com.github.wephotos.webwork.user.client.entity.po;

import java.io.Serializable;
import java.util.List;

import com.github.wephotos.webwork.user.entity.enums.NodeTypeEnum;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户角色查询参数
 * 提示：{@link #appId} {@link #appCode} 指应用关联的角色，并不是特指应用下创建的角色
 * @author TianQi
 *
 */
@Getter
@Setter
@ToString
public class UserRoleQueryPO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	private Integer userId;
	
	/**
	 * 应用ID，筛选应用角色
	 */
	private Integer appId;
	
	/**
	 * 应用代码，筛选应用角色
	 */
	private String appCode;
	
	/**
	 * 内部属性，赋值会被忽略
	 * 角色关联的用户集合（用户/部门/单位）
	 */
	private List<RoleUserIdTypeQueryPO> userIds;
	
	
	/**
	 * 角色关联的用户和类型参数
	 * @author TianQi
	 *
	 */
	@Getter
	@Setter
	@ToString
	public static class RoleUserIdTypeQueryPO {
		
		/**
		 * 用户ID
		 */
		private Integer id;
		
		/**
		 * 用户类型
		 * {@link NodeTypeEnum#USER}
		 * {@link NodeTypeEnum#DEPT}
		 * {@link NodeTypeEnum#GROUP}
		 */
		private Integer type;
	}
	
}
