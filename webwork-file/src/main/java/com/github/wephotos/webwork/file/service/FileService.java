package com.github.wephotos.webwork.file.service;


import com.github.wephotos.webwork.file.entity.UploadResult;
import com.github.wephotos.webwork.file.entity.WebworkFile;
import com.github.wephotos.webwork.file.mapper.FileMapper;
import com.github.wephotos.webwork.file.stor.FileStor;
import com.github.wephotos.webwork.utils.StringUtils;
import com.github.wephotos.webwork.utils.WebworkUtils;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

/**
 * @author chengzi
 * @date 2021-03-08 19:59
 */
@Getter
@Setter
@Service
public class FileService {

	public FileService() {
		
	}
	
	public FileService(FileStor fileStor) {
		this.fileStor = fileStor;
	}
	
	/**
	 * 文件存储实例
	 */
	@Resource
	private FileStor fileStor;
	
	@Resource
	private FileMapper fileMapper;

	/**
	 * 上传
	 * @param file
	 * @return
	 * @throws IOException 
	 */
	public UploadResult upload(WebworkFile file) throws IOException {
		if(StringUtils.isBlank(file.getOwner())){
			throw new IllegalArgumentException("附件关联外键不能为空");
		}
		String objectName = fileStor.getNewObjectName(file.getName());
		file.setObjectName(objectName);
		fileStor.storage(file);
		file.setId(WebworkUtils.uuid());
		file.setCreateTime(WebworkUtils.timestamp());
		fileMapper.insert(file);
		return UploadResult.builder()
				.id(file.getId())
				.name(file.getName())
				.objectName(file.getObjectName()).build();
	}
	/**
	 * 删除
	 * @param id
	 * @return
	 * @throws IOException 
	 */
    public int deleteByPrimaryKey(String id) throws IOException {
    	WebworkFile file = fileMapper.selectByPrimaryKey(id);
    	fileStor.delete(file.getObjectName());
    	return fileMapper.deleteByPrimaryKey(id);
    }
    
    /**
     * 获取文件，包含文件流
     * @param id
     * @return
     * @throws IOException 
     */
    public WebworkFile getFile(String id) throws IOException {
    	WebworkFile file = fileMapper.selectByPrimaryKey(id);
    	file.setInputStream(fileStor.get(file.getObjectName()));
    	return file;
    }
    
    /**
     * 获取文件
     * @param owner
     * @return
     */
    public List<WebworkFile> list(String owner){
    	return fileMapper.list(owner);
    }

}
