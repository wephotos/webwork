package com.github.wephotos.webwork.user.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 数据字典
 * @author TQ
 *
 */
@Getter
@Setter
@ToString
@TableName(value = "sys_dictionary")
public class Dictionary implements Serializable {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * PK
	 */
	private Integer id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 字典值
	 */
	private String value;
	/**
	 * 层级编码
	 */
	private String code;
	/**
	 * 排序号
	 */
	private Integer sort;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 父级ID
	 */
	private Integer parentId;
	/**
	 * 父级名称
	 */
	@TableField(exist = false)
	private String parentName;
	
	/**
	 * 创建用户
	 */
	private String createUser;
	
	/**
	 * 更新用户
	 */
	private String updateUser;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 更新时间
	 */
	private Date updateTime;
}
