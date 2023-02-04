package com.github.wephotos.webwork.photos.entity.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户档案视图对象
 * @author TianQi
 *
 */
@Getter
@Setter
@ToString
public class UserProfileVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 使用空间
	 */
	private Long usageSpace;
	
	/**
	 * 照片总空间
	 */
	private Long totalSpace;
	
	/**
	 * 已创建相册数量
	 */
	private Integer albumCount;
	
	/**
	 * 可创建相册数量
	 */
	private Integer maxAlbumCount;
}
