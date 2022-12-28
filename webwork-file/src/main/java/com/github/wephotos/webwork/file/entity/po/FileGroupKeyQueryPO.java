package com.github.wephotos.webwork.file.entity.po;

import com.github.wephotos.webwork.file.entity.FileGroupKey;

/**
 * 文件分组唯一标识查询参数
 * @author TianQi
 *
 */
public class FileGroupKeyQueryPO extends FileGroupKey {

	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "FileGroupKeyQueryPO [getFileGroup()=" + getFileGroup() + ", getFileGroupKey()=" + getFileGroupKey()
				+ "]";
	}

}
