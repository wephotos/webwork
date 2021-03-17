package com.github.wephotos.webwork.file.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UploadResult {

	/**
	 * PK
	 */
	private String id;
	/**
	 * 附件名称
	 */
	private String name;
	/**
	 * 存储对象名
	 */
	private String objectName;
}
