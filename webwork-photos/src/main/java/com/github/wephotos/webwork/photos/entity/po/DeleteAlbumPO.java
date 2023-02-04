package com.github.wephotos.webwork.photos.entity.po;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 删除相册参数
 * @author TianQi
 *
 */
@Getter
@Setter
@ToString
public class DeleteAlbumPO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 相册ID
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
}
