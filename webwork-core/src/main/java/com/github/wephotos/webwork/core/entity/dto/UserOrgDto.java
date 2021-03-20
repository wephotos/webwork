package com.github.wephotos.webwork.core.entity.dto;

import com.github.wephotos.webwork.core.entity.UserOrg;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author chengzi
 * @date 2021-02-02 10:49
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class UserOrgDto extends UserOrg {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 部门名称
     */
    private String depName;
    /**
     * 组织名称
     */
    private String groupName;
}
