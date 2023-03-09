package com.github.wephotos.webwork.user.client.entity.po;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 更新用户信息参数
 * 
 * @author TianQi
 *
 */
@Getter
@Setter
@ToString
public class UpdateUserInfoPO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
     * 主键
     */
    private Integer id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 联系方式
     */
    private String phone;
    /**
     * 职务
     */
    private String post;
}
