package com.github.wephotos.webwork.file.service;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.github.wephotos.webwork.file.entity.UploadResult;
import com.github.wephotos.webwork.file.entity.WebworkFile;
import com.github.wephotos.webwork.file.facade.FileFacade;
import com.github.wephotos.webwork.file.facade.po.UploadFilePO;
import com.github.wephotos.webwork.file.facade.ro.UploadResultRO;
import com.github.wephotos.webwork.utils.BeanUtils;

/**
 * 文件服务对外接口实现
 * @author TianQi
 *
 */
@Component("fileFacade")
public class FileFacadeImpl implements FileFacade {

	@Resource
	private FileService fileService;

	@Override
	public UploadResultRO upload(UploadFilePO uploadFilePO) throws IOException {
		WebworkFile file = BeanUtils.toObject(uploadFilePO, WebworkFile.class);
		// 文件流
		file.setInputStream(uploadFilePO.getInputStream());
		UploadResult uploadResult = fileService.upload(file);
		return BeanUtils.toObject(uploadResult, UploadResultRO.class);
	}
	
}
