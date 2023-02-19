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
 * 文档用户
 * @author TianQi
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "webwork_photos_album")
public class Album implements Serializable {
	
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
	 * 相册状态
	 * @see {@link EntityState}
	 */
	private Integer state;
	
	/**
	 * 用户ID
	 */
	private Integer userId;
	
	/**
	 * 用户姓名
	 */
	private String userName;
	
	/**
	 * 相册封面
	 */
	private String cover;
	
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
