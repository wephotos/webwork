package com.github.wephotos.webwork.photos.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.wephotos.webwork.schema.entity.EntityState;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 照片数据实体
 * @author TianQi
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "webwork_photos_album_photo")
public class AlbumPhoto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	private Integer id;
	
	/**
	 * 照片名称
	 */
	private String name;
	
	/**
	 * 相册状态
	 * @see {@link EntityState}
	 */
	private Integer state;
	
	/**
	 * 相册ID
	 */
	private Integer albumId;
	
	/**
	 * 描述信息
	 */
	private String description;
	
	/**
	 * 照片大小 - Byte
	 */
	private Integer photoSize;
	
	/**
	 * 照片地址
	 */
	private String objectName;
	
	/**
	 * 缩略图地址
	 */
	private String thumbObjectName;
	
	/**
	 * 用户ID
	 */
	private Integer userId;
	
	/**
	 * 用户姓名
	 */
	private String userName;
	
	/**
	 * 置顶时间
	 */
	private Date topTime;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 更新时间
	 */
	private Date updateTime;
}
