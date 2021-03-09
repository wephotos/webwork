package com.github.wephotos.webwork.core.entity.dto;

import com.github.wephotos.webwork.core.entity.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author chengzi
 * @date 2021-02-17 19:46
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class RoleDto extends Role {
    /**
     * 菜单id
     */
    private String[] menuIds;
}
