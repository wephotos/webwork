package com.github.wephotos.webwork.file.stor;

import lombok.Getter;

/**
 * 存储类型
 * 
 * @author TQ
 *
 */
@Getter
public enum StorDialect {

	DISK("本地磁盘", "disk"), ALI_CLOUD("阿里云", "ali"), HUAWEI_CLOUD("华为云", "huawei"), QING_CLOUD("青云", "qing");

	private StorDialect(String name, String dialect) {
		this.name = name;
		this.dialect = dialect;
	}

	private String name;
	private String dialect;
}
