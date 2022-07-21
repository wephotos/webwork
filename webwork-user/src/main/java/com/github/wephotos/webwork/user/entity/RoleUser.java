package com.github.wephotos.webwork.user.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
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
     * 用户类型
     */
    private Integer userType;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 角色用户类型
     * @author TQ
     *
     */
    public static enum RoleUserType {
    	USER(1, "用户"),
    	DEPT(2, "部门"),
    	GROUP(3, "单位");
    	
    	/**
    	 * 构造方法
    	 * @param type 类型
    	 * @param name 名称
    	 */
    	RoleUserType(int type, String name){
    		this.type = type;
    		this.name = name;
    	}
    	/***
    	 * 类型
    	 */
    	private int type;
    	/**
    	 * 名称
    	 */
    	private String name;
    	
    	public int getType() {
			return type;
		}
    	
    	public String getName() {
			return name;
		}
    }
}
