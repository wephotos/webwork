package com.github.wephotos.webwork.docs.utils;

import com.github.wephotos.webwork.schema.exception.StateCode;

/**
 * 文档错误码定义
 * @author TianQi
 *
 */
public final class DocStateCode {
	
	public static final StateCode VERSION_EXPIRED = new StateCode(200101, "过期的版本，文档已经被修改过");
	public static final StateCode NO_PERMISSION_EDIT = new StateCode(200102, "对文档没有编辑权限");
	public static final StateCode NO_PERMISSION_DELETE = new StateCode(200103, "对文档没有删除权限");

}
