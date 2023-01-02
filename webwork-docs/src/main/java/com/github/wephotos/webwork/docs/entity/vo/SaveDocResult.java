package com.github.wephotos.webwork.docs.entity.vo;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 保存文档返回结果
 * @author TianQi
 *
 */
@Getter
@Setter
@Builder
@ToString
public class SaveDocResult implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 文档ID
	 */
	private Integer id;
	
	/**
	 * 文档版本
	 */
	private Integer versionNo;
}
