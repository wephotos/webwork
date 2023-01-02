package com.github.wephotos.webwork.docs.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 文档数据实体
 * @author TQ
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "webwork_document")
public class Document implements Serializable {

	/**
	 * 序列化版本号
	 */
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
	 * 文档状态
	 */
	private Integer state;
	
	/**
	 * 是否公开
	 */
	private Boolean open;
	
	/**
	 * 子标题
	 */
	private String subtitle;
	
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
