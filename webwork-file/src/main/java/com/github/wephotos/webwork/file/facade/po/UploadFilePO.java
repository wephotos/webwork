package com.github.wephotos.webwork.file.facade.po;

import java.io.InputStream;
import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 上传文件参数
 * @author TianQi
 *
 */
@Getter
@Setter
@ToString
public class UploadFilePO implements Serializable {

	private static final long serialVersionUID = 1L;
	
    /**
     * 文件分组
     */
    private String fileGroup;
    /**
     * 文件分组内的唯一标识
     */
    private String fileGroupKey;
    
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
    private Integer userId;
    /**
     * 上传人姓名
     */
    private String userName;

    /**
     * 文件流
     */
    private transient InputStream inputStream;
}
