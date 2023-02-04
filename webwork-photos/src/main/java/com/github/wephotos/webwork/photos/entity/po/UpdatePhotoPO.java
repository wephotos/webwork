package com.github.wephotos.webwork.photos.entity.po;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 更新照片参数
 * @author TianQi
 *
 */
@Getter
@Setter
@ToString
public class UpdatePhotoPO implements Serializable {

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
	 * 用户ID
	 */
	private Integer userId;
	
	/**
	 * 用户姓名
	 */
	private String userName;
}
