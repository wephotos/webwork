package com.github.wephotos.webwork.user.api.entity.po;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserPo implements Serializable {
    /**
	 * 
	 */
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
    private String password;
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
     * 部门id
     */
    private Integer deptId;
}
