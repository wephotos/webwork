package com.github.wephotos.webwork.entity;

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
    private String id;
    private String name;
    private String parentId;
    private Integer type;
    private Integer status;
    private String code;
}
