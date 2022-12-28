package com.github.wephotos.webwork.file.entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.github.wephotos.webwork.utils.StringUtils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 文件组定义
 * @author TianQi
 *
 */
@Getter
@Setter
@ToString
public final class FileGroup {
	
	/**
	 * 注册的文件组
	 */
	private static final Map<String, FileGroup> lookup = new ConcurrentHashMap<>();
	
	/**
	 * 默认文件组
	 */
	public static final FileGroup DEFAULT_GROUP = new FileGroup("DEFAULT", "默认文件组");


	/**
	 * 用于注册文件组
	 * @param name 名称
	 * @param desc 描述
	 */
	public FileGroup(String name, String desc) {
		if(StringUtils.isBlank(name)) {
			throw new IllegalArgumentException("文件组名称不能为空");
		}
		if(lookup.containsKey(name)) {
			throw new IllegalArgumentException("文件组已经存在:" + name);
		}
		this.name = name;
		this.desc = desc;
		lookup.putIfAbsent(this.name, this);
	}
	
	/**
	 * 判断文件组是否存在
	 * @param name 文件组名称
	 * @return 存在返回 true
	 */
	public static boolean containsGroup(String name) {
		return lookup.containsKey(name);
	}
	
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 描述
	 */
	private String desc;
}
