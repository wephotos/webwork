package com.github.wephotos.webwork.photos.entity.po;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 增加相册照片数量参数
 * @author TianQi
 *
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PlusPhotoCountPO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 相册ID
	 */
	private Integer id;
	
	/**
	 * 照片数量，可为负数
	 */
	private Integer count;
	
	private Integer userId;
	
	private String userName;
}
