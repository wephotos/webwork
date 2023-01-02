package com.github.wephotos.webwork.docs.entity.po;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 文档查询参数
 * @author TianQi
 *
 */
@Getter
@Setter
@ToString
public class DocQueryPO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	private Integer userId;
	
	/**
	 * 搜索关键字
	 */
	private String keyword;
}
