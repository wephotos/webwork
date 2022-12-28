package com.github.wephotos.webwork.user.client.entity.po;

import java.io.Serializable;
import java.util.List;

import com.github.wephotos.webwork.user.entity.enums.NodeTypeEnum;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户资源查询参数
 * @author TianQi
 *
 */
@Getter
@Setter
@ToString
public class UserResoQueryPO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 应用ID
	 * 优先于 {@link #appCode}
	 */
	private Integer appId;
	
	/**
	 * 用户自定义的应用代码
	 */
	private String appCode;
	
	/**
	 * 用户ID
	 */
	private Integer userId;
	
	/**
	 * 资源类型
	 * 应用、模块、功能
	 * {@link NodeTypeEnum#APP}
	 * {@link NodeTypeEnum#MODULE}
	 * {@link NodeTypeEnum#FUNCTION}
	 */
	private Integer resoType;
	
	/**
	 * 内部使用，外部传值将被忽略
	 */
	private List<Integer> roleIds;
}
