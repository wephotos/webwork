package com.github.wephotos.webwork.photos.entity.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 照片视图对象
 * @author TianQi
 *
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AlbumPhotoVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 照片ID
	 */
	private Integer id;
	
	/**
	 * 照片名称
	 */
	private String name;
	
	/**
	 * 照片描述信息
	 */
	private String description;
	
	/**
	 * 照片存储对象名，可用于访问图片
	 */
	private String objectName;
	
	/**
	 * 缩略图，照片存储对象名，可用于访问图片
	 */
	private String thumbObjectName;
	
	/**
	 * 创建时间即照片上传时间
	 */
	private Date createTime;
}
