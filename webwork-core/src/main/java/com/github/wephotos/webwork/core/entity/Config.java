package com.github.wephotos.webwork.core.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 系统配置
 * @author TQ
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "sys_config")
public class Config implements Serializable {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * PK
	 */
	private String id;
	/**
	 * 键
	 */
	private String name;
	
	/**
	 * 值
	 */
	private String value;
	
	/**
	 * 当前配置环境
	 */
	private String profile;
	
	/**
	 * 排序号
	 */
	private Integer sort;
	
	/**
	 * 状态
	 */
	private Integer status;
}
