package com.github.wephotos.webwork.entity;

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
    private String id;
    private String name;
    private String code;
    private Integer status;
    private Integer sort;
    private String remark;
}
