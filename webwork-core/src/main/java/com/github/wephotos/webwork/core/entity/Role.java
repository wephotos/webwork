package com.github.wephotos.webwork.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 角色
 *
 * @author chengzi
 * @date 2021-01-25 16:34
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_role")
public class Role {
    /**
     * 主键
     */
    private String id;
    /**
     * name
     */
    private String name;
    /**
     * status
     */
    private String code;
    /**
     * remark
     */
    private Integer status;
    /**
     * sort
     */
    private Integer sort;
    /**
     *
     */
    private String remark;
}
