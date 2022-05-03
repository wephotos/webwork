package com.github.wephotos.webwork.user.api.entity.ro;

import com.github.wephotos.webwork.user.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户
 *
 * @author chengzi
 * @date 2021-01-25 16:34
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserRo extends User {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 人员部门内排序
	 */
	private Integer sort;
	/**
	 * 部门id
	 */
	private Integer deptId;
	/**
     * 部门名称
     */
    private String deptName;
    /**
     * 单位ID
     */
    private Integer orgId;
    /**
     * 组织名称
     */
    private String orgName;
}
