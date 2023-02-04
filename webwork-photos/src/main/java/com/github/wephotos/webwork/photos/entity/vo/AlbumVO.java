package com.github.wephotos.webwork.photos.entity.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 相册视图对象
 * @author TianQi
 *
 */
@Getter
@Setter
@ToString
public class AlbumVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键(相册ID)
	 */
	private Integer id;
	
	/**
	 * 相册名称
	 */
	private String name;
	
	/**
	 * 相册封面
	 */
	private String coverPhoto;
	
	/**
	 * 相册使用空间
	 */
	private Long usageSpace;
	
	/**
	 * 当前照片数量
	 */
	private Integer photoCount;
	
	/**
	 * 相册照片数量上限
	 */
	private Integer maxPhotoCount;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 更新时间
	 */
	private Date updateTime;
}
