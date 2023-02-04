package com.github.wephotos.webwork.photos.entity.po;

import java.io.InputStream;
import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 上传照片入参
 * @author TianQi
 *
 */
@Getter
@Setter
@ToString
public class UploadPhotoPO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 照片ID，更新时使用
	 */
	private Integer id;
	/**
	 * 照片名称
	 */
	private String name;
	
	/**
	 * 描述信息
	 */
	private String description;
	
	/**
	 * 照片大小
	 */
	private Integer photoSize;
	
	/**
	 * 相册ID
	 */
	private Integer albumId;
	
	/**
	 * 用户ID
	 */
	private Integer userId;
	
	/**
	 * 用户姓名
	 */
	private String userName;
	
	/**
	 * 图片类型
	 */
	private String contentType;
	
	/**
	 * 上传照片流
	 */
	private transient InputStream inputStream;
}
