package com.github.wephotos.webwork.docs.entity.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 列表文档结构定义
 * @author TianQi
 *
 */
@Getter
@Setter
@ToString
public class ListDocVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 文档ID
	 */
	private Integer id;
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 子标题
	 */
	private String subtitle;
	
	/**
	 * 创建人
	 */
	private String createUser;
	
	/**
	 * 更新人
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
