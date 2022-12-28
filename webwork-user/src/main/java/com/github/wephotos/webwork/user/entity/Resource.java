package com.github.wephotos.webwork.user.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.wephotos.webwork.schema.entity.EntityState;
import com.github.wephotos.webwork.user.entity.enums.NodeTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 资源菜单
 *
 * @author chengzi
 * @date 2021-01-25 16:34
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_resource")
public class Resource {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 资源类型
     * @see {@link NodeTypeEnum}
     */
    private Integer type;
    /**
     * 权限code
     */
    private String permission;
    /**
     * 上下级别关系
     */
    private String code;
    /**
     * 上级菜单
     */
    private Integer parentId;
    /**
     * 父节点类型
     * @see NodeTypeEnum
     */
    private Integer parentType;
    /**
     * 资源状态
     * @see {@link EntityState}
     */
    private Integer status;
    /**
     * url
     */
    private String url;
    /**
     * 图标
     */
    private String icon;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 是否通用权限
     */
    private boolean common;
    /**
     * 备注
     */
    private String remark;
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
