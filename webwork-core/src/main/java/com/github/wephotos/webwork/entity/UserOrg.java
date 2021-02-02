package com.github.wephotos.webwork.entity;

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
@TableName(value = "sys_user_org")
public class UserOrg {
    private String id;
    private String resourceId;
    private String orgId;
}
