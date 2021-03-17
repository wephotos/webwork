package com.github.wephotos.webwork.file.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.InputStream;
import java.util.Date;

/**
 * @author chengzi
 * @date 2021-03-08 20:10
 */
@Getter
@Setter
@ToString
public class WebworkFile {
	/**
	 * 主键
	 */
    private String id;
    /**
	 * 附件主体
	 */
    private String owner;
    /**
	 * 文件名
	 */
    private String name;
    /**
	 * 文件大小
	 */
    private long size;
    /**
	 * 文件存储对象名
	 */
    private String objectName;
    /**
	 * 文件内容类型
	 */
    private String contentType;
    /**
     * 上传人标识
     */
    private String userId;
    /**
     * 上传人姓名
     */
    private String userName;
    /**
	 * 上传时间
	 */
    private Date createTime;

    /**
     * 文件流
     */
    private InputStream inputStream;
}
