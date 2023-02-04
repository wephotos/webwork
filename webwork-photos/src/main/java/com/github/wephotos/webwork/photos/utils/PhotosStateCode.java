package com.github.wephotos.webwork.photos.utils;

import com.github.wephotos.webwork.schema.exception.StateCode;

/**
 * 照片错误码定义
 * @author TianQi
 *
 */
public final class PhotosStateCode {

	public static final StateCode NOT_ENOUGH_STORAGE_SPACE = new StateCode(301001, "用户存储空间不足");
	public static final StateCode GREATER_THAN_MAX_PHOTO_COUNT = new StateCode(301002, "相册上传照片超出数量限制");
	public static final StateCode GREATER_THAN_MAX_ALBUM_COUNT = new StateCode(301003, "用户创建相册超出数量限制");
	public static final StateCode NOT_DELETE_ALBUM_HAS_PHOTO = new StateCode(301004, "不能删除包含照片的相册");
}
