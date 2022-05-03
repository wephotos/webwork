package com.github.wephotos.webwork.user.api.entity.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户查询
 * @author TQ
 *
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserQueryPo {

	/**
	 * 用户ID
	 */
	private Integer id;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 账号
	 */
	private String account;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 手机
	 */
	private String phone;
	/**
	 * 部门ID
	 */
	private String deptid;
	/**
	 * 组织编码
	 */
	private String orgcode;
}
