package com.github.wephotos.webwork.user.api.entity.po;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.Setter;

/**
 * 组织查询条件
 * @author TQ
 *
 */
@Getter
@Setter
@Builder
@ToString
public class OrganizationQueryPo {
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
	 * 非某个类型
	 */
	private Integer neType;
	/**
	 * 父节点id
	 */
	private Integer parentId;
}
