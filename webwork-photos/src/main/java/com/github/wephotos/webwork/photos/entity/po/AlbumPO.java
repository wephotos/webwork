package com.github.wephotos.webwork.photos.entity.po;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 创建或修改相册入参
 * @author TianQi
 *
 */
@Getter
@Setter
@ToString
public class AlbumPO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 相册ID
	 */
	private Integer id;
	
	/**
	 * 相册名称
	 */
	private String name;
	
	/**
	 * 用户ID
	 */
	private Integer userId;
	
	/**
	 * 用户姓名
	 */
	private String userName;
}
