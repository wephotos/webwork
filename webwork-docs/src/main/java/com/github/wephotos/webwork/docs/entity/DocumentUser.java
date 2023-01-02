package com.github.wephotos.webwork.docs.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 文档用户
 * @author TianQi
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "webwork_document_user")
public class DocumentUser implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private Integer id;
	
	/**
	 * 文档ID
	 */
	private Integer docId;
	
	/**
	 * 用户ID
	 */
	private Integer userId;
	
	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 用户访问文档类型
	 * {@link UserAccessType}
	 */
	private Integer accessType;
}
