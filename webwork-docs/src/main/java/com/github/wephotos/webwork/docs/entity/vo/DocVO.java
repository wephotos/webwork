package com.github.wephotos.webwork.docs.entity.vo;

import java.io.Serializable;
import java.util.Date;

import com.github.wephotos.webwork.docs.entity.ContentType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 文档返回结构定义
 * @author TianQi
 *
 */
@Getter
@Setter
@ToString
public class DocVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 可否编辑
	 */
	private boolean canEdit;
	
	/**
	 * 可否删除
	 */
	private boolean canDelete;
	
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
