package com.github.wephotos.webwork.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 角色资源关联表
 *
 * @author chengzi
 * @date 2021-01-25 16:34
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_role_resource")
public class RoleResource {
    /**
     * 主键
     */
    private String id;
    /**
     * 角色id
     */
    private String roleId;
    /**
     * 菜单id
     */
    private String resourceId;
}
