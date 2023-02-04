package com.github.wephotos.webwork.file.facade;

import java.io.IOException;

import com.github.wephotos.webwork.file.facade.po.UploadFilePO;
import com.github.wephotos.webwork.file.facade.ro.UploadResultRO;

/**
 * 文件服务对外接口
 * @author TianQi
 *
 */
public interface FileFacade {

	/**
	 * 上传文件
	 * @param uploadFilePO 上传文件入参
	 * @return 上传文件出参
	 * @throws IOException I/O异常
	 */
	UploadResultRO upload(UploadFilePO uploadFilePO) throws IOException;
}
