package com.github.wephotos.webwork.photos.entity.po;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 设置相册封面参数
 * @author TianQi
 *
 */
@Getter
@Setter
@ToString
public class CoverPO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 封面图片
	 */
	private String cover;
	
	/**
	 * 相册ID
	 */
	private Integer albumId;
}
