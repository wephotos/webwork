package com.github.wephotos.webwork.photos.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 照片用户数据
 * @author TQ
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "webwork_photos_user_profile")
public class UserProfile implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	private Integer id;
	
	/**
	 * 用户ID
	 */
	private Integer userId;
	
	/**
	 * 用户姓名
	 */
	private String userName;
	
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
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 更新时间
	 */
	private Date updateTime;
}
