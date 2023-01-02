package com.github.wephotos.webwork.docs.entity.po;

import java.io.Serializable;

import com.github.wephotos.webwork.docs.entity.ContentType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 保存或更新文档的参数
 * @author TianQi
 *
 */
@Getter
@Setter
@ToString
public class DocumentPO implements Serializable {

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
	 * 是否公开
	 */
	private Boolean open;
	
	/**
	 * 文档内容
	 */
	private String content;
	
	/**
	 * 文档内容类型
	 * {@link ContentType}
	 */
	private Integer contentType;
	
	/**
	 * 版本号
	 */
	private Integer versionNo;
	
	/**
	 * 用户ID
	 */
	private Integer userId;
	
	/**
	 * 用户姓名
	 */
	private String userName;
}
