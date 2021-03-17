package com.github.wephotos.webwork.file.stor;

import java.io.IOException;
import java.io.InputStream;

import com.github.wephotos.webwork.file.entity.WebworkFile;

/**
 * 文件存储接口
 * 
 * @author TQ
 *
 */
public interface FileStor {

	/**
	 * 获取存储类型枚举实例
	 * @return 枚举实例
	 */
	StorDialect getStorDialect();
	/**
	 * 生成新的存储对象名称
	 * @param filename 原始文件名
	 * @return 存储对象名
	 */
	String getNewObjectName(String filename);

	/**
	 * 存储文件
	 * @param file 文件对象
	 * @throws IOException IO异常
	 */
	void storage(WebworkFile file) throws IOException;

	/**
	 * 删除文件
	 * @param objectName 文件对象名
	 * @throws IOException IO异常
	 */
	void delete(String objectName) throws IOException;

	/**
	 * 获取文件流
	 * @param objectName 文件对象名
	 * @return 文件流
	 * @throws IOException IO异常
	 */
	InputStream get(String objectName) throws IOException;
}
