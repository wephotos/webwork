package com.github.wephotos.webwork.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
     * 1:菜单，2:按钮权限
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
     * 状态  启用：1，0：禁用，2：删除
     */
    private Integer status;
    /**
     * url
     */
    private String url;
    /**
     * 备注
     */
    private String icon;
    /**
     * 序号
     */
    private Integer sort;
    /**
     * 备注
     */
    private String remark;
}
