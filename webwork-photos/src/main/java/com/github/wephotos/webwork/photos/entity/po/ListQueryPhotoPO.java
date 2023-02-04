package com.github.wephotos.webwork.photos.entity.po;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 查询照片列表参数
 * @author TianQi
 *
 */
@Getter
@Setter
@ToString
public class ListQueryPhotoPO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 相册ID
	 */
	private Integer albumId;
}
