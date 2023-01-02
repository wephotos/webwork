package com.github.wephotos.webwork.docs.entity.po;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DeleteDocPO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 文档ID
	 */
	private Integer id;
	
	/**
	 * 当前用户ID
	 */
	private Integer userId;
}
