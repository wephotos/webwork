package com.github.wephotos.webwork.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;

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
     * 0:虚拟机构, 1:单位, 2:部门
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
}
