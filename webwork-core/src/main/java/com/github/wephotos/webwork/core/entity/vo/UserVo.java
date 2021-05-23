package com.github.wephotos.webwork.core.entity.vo;

import com.github.wephotos.webwork.core.entity.User;

import lombok.Getter;
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
public class UserVo extends User {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 人员部门内排序
	 */
	private int sort;
	/**
	 * 部门id
	 */
	private String deptId;
	/**
     * 部门名称
     */
    private String deptName;
    /**
     * 单位ID
     */
    private String orgId;
    /**
     * 组织名称
     */
    private String orgName;
}
