package com.github.wephotos.webwork.user.api.entity.po;

import com.github.wephotos.webwork.user.entity.User;

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
public class UserPo extends User {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    /**
     * 部门id
     */
    private Integer deptId;
}
