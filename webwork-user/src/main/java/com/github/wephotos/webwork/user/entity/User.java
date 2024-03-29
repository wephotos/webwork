package com.github.wephotos.webwork.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.wephotos.webwork.schema.entity.EntityState;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 *
 * @author chengzi
 * @date 2021-01-25 16:34
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_user")
public class User implements Serializable {
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
     * @see EntityState
     */
    private Integer status;
    
    /**
	 * 创建用户
	 */
	private String createUser;
	
	/**
	 * 更新用户
	 */
	private String updateUser;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;

}
