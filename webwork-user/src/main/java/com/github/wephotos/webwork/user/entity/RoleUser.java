package com.github.wephotos.webwork.user.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.wephotos.webwork.user.entity.enums.NodeTypeEnum;

import lombok.*;

/**
 * 角色用户(部门/单位)关联
 *
 * @author chengzi
 * @date 2021-01-25 16:34
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_role_user")
public class RoleUser implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 主键
     */
    private Integer id;
    /**
     * 角色id
     */
    private Integer roleId;
    /**
     * 用户id
     */
    private Integer userId;
    
    /**
     * 用户名称
     */
    private String userName;
    
    /**
     * 用户类型, 用户，部门，单位
     * @see 
     * {@link NodeTypeEnum#USER} 
     * {@link NodeTypeEnum#DEPT} 
     * {@link NodeTypeEnum#GROUP}
     */
    private Integer userType;
    /**
	 * 创建用户
	 */
	private String createUser;
    /**
     * 创建时间
     */
    private Date createTime;
    
}
