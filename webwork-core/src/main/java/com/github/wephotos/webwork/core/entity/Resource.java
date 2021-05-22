package com.github.wephotos.webwork.core.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.wephotos.webwork.http.EntityState;

import lombok.*;

/**
 * 资源菜单
 *
 * @author chengzi
 * @date 2021-01-25 16:34
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_resource")
public class Resource {
    /**
     * 主键
     */
    private String id;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * {@link ResourceType}
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
    private String parentId;
    /**
     * {@link EntityState}
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
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}
