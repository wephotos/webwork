package com.github.wephotos.webwork.user.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.wephotos.webwork.user.entity.enums.NodeTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 组织部门
 *
 * @author chengzi
 * @date 2021-01-25 16:34
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_organization")
public class Organization {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 编码
     */
    private String code;
    /**
     * 上级id
     */
    private Integer parentId;
    /**
     * 父节点类型
     * {@link NodeTypeEnum}
     */
    private Integer parentType;
    /**
     * 节点类型
     * @see NodeTypeEnum
     */
    private Integer type;
    /**
     * 1:启用，0:禁用
     */
    private Integer status;
    /**
     * 排序
     */
    private Integer sort;
    
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
	 * 更新时间
	 */
	private Date updateTime;
}
