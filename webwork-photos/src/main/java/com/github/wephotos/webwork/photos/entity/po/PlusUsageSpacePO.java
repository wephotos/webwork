package com.github.wephotos.webwork.photos.entity.po;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 增加用户使用空间参数
 * @author TianQi
 *
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PlusUsageSpacePO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 相册ID
	 */
	private Integer id;
	
	/**
	 * 增加空间大小，可为负数
	 */
	private Integer size;
	
	private Integer userId;
	
	private String userName;
}
