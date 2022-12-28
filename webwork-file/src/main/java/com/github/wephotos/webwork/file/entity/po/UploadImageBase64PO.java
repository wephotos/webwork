package com.github.wephotos.webwork.file.entity.po;

import com.github.wephotos.webwork.file.entity.FileGroupKey;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 上传图片的Base64字符串
 * @author TianQi
 *
 */
@Getter
@Setter
@ToString
public class UploadImageBase64PO extends FileGroupKey {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 图片的Base64字符串
	 */
	private String base64;
    /**
     * 文件分组
     */
    private String fileGroup;
    /**
     * 同一组的唯一键
     */
    private String fileGroupKey;
}
