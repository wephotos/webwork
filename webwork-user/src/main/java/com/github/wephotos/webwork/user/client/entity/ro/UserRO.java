package com.github.wephotos.webwork.user.client.entity.ro;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户信息
 * @author TianQi
 *
 */
@Getter
@Setter
@ToString
public class UserRO implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
     * 主键
     */
    private Integer id;
    /**
     * 登录名
     */
    private String account;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 密码
     */
    //private String password;
    /**
     * 中文名
     */
    private String name;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 联系方式
     */
    private String phone;
    /**
     * 职务
     */
    private String post;
    /**
     * 状态 启用：1，0：禁用，2：删除
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    
    /*------------------------------扩展属性--------------------------------*/
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
