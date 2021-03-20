package com.github.wephotos.webwork.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

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
    private String id;
    /**
     * 名称
     */
    private String name;
    /**
     * 上级id
     */
    private String parentId;
    /**
     * 1:组织，2:部门
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
     * 编码
     */
    private String code;
}
