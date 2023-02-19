package com.github.wephotos.webwork.file.service;


import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.github.wephotos.webwork.file.entity.FileGroup;
import com.github.wephotos.webwork.file.entity.UploadResult;
import com.github.wephotos.webwork.file.entity.WebworkFile;
import com.github.wephotos.webwork.file.entity.po.FileGroupKeyQueryPO;
import com.github.wephotos.webwork.file.mapper.FileMapper;
import com.github.wephotos.webwork.file.stor.FileStor;
import com.github.wephotos.webwork.logging.LoggerFactory;
import com.github.wephotos.webwork.schema.entity.EntityState;
import com.github.wephotos.webwork.schema.exception.StateCode;
import com.github.wephotos.webwork.schema.exception.WebworkRuntimeException;
import com.github.wephotos.webwork.utils.StringUtils;
import com.github.wephotos.webwork.utils.WebworkUtils;

/**
 * 文件服务
 * @author TianQi
 *
 */
@Service
public class FileService {
	
	private static final Logger log = LoggerFactory.getLogger(FileService.class);

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
     *
     * @param file 附件
     * @return {@link UploadResult}
     * @throws IOException IO异常
     */
    public UploadResult upload(WebworkFile file) throws IOException {
    	log.info("上传文件: {}", file);
        // 是否覆盖原文件
        boolean isOverwrite = false;
        String objectName = file.getObjectName();
        if(StringUtils.isBlank(objectName)) {
        	objectName = fileStor.getNewObjectName(file.getName());
        	file.setObjectName(objectName);
        }else {
        	WebworkFile ofile = fileMapper.selectByObjectName(objectName);
        	if(ofile != null) {
        		isOverwrite = true;
        		file.setId(ofile.getId());
        		fileMapper.updateByPrimaryKeySelective(file);
        	}
        }
        if(!isOverwrite) {
        	// 默认文件组
        	if(StringUtils.isBlank(file.getFileGroup())) {
        		file.setFileGroup(FileGroup.DEFAULT_GROUP.getName());
        	}else if(!FileGroup.containsGroup(file.getFileGroup())) {
        		throw new WebworkRuntimeException(StateCode.PARAMETER_ILLEGAL, "文件组未注册:" + file.getFileGroup());
        	}
        	// 文件组的key为空时使用32位UUID作为key
        	if(StringUtils.isBlank(file.getFileGroupKey())) {
        		file.setFileGroupKey(UUID.randomUUID().toString().replace("-", ""));
        	}
        	file.setStatus(EntityState.NORMAL.getCode());
        	file.setCreateTime(WebworkUtils.nowTime());
        	fileMapper.insert(file);
        }
        //存储文件
        fileStor.storage(file);
        // 返回主键 文件名 存储对象名
        return UploadResult.builder()
                           .id(file.getId())
                           .name(file.getName())
                           //.fileGroup(file.getFileGroup())
                           //.fileGroupKey(file.getFileGroupKey())
                           .objectName(file.getObjectName()).build();
    }

    /**
     * 逻辑删除
     *
     * @param id 文件ID
     * 
     * @return 删除成功返回 true
     */
    public boolean logicalDelete(Integer id) {
    	log.info("逻辑删除文件, id = {}", id);
        WebworkFile file = new WebworkFile();
        file.setId(id);
        file.setStatus(EntityState.DELETED.getCode());
        return fileMapper.updateByPrimaryKeySelective(file) == 1;
    }
    
    /**
     * 物理删除文件
     *
     * @param id 文件ID
     * @return 删除成功返回 true
     * @throws IOException IO异常
     */
    public boolean physicalDelete(Integer id) throws IOException {
    	log.info("物理删除文件, id = {}", id);
        WebworkFile file = fileMapper.selectByPrimaryKey(id);
        fileStor.delete(file.getObjectName());
        return fileMapper.deleteByPrimaryKey(id) == 1;
    }

    /**
     * 获取文件，包含文件流
     *
     * @param id 附件id
     * @return {@link WebworkFile}
     * @throws IOException IO异常
     */
    public WebworkFile getFile(Integer id) throws IOException {
    	log.info("获取文件, id = {}", id);
        WebworkFile file = fileMapper.selectByPrimaryKey(id);
        file.setInputStream(fileStor.get(file.getObjectName()));
        return file;
    }
    
    /**
     * 获取文件，包含文件流
     *
     * @param objectName 存储对象名
     * @return {@link WebworkFile}
     * @throws IOException IO异常
     */
    public WebworkFile getFileByObjectName(String objectName) throws IOException {
    	log.info("获取文件, objectName = {}", objectName);
        WebworkFile file = fileMapper.selectByObjectName(objectName);
        file.setInputStream(fileStor.get(file.getObjectName()));
        return file;
    }

    /**
     * 获取文件组下的文件
     *
     * @param fileGroupKeyQueryPO 文件组信息
     * @return {@link List<WebworkFile>}
     */
    public List<WebworkFile> listByFileGroupKey(FileGroupKeyQueryPO fileGroupKeyQueryPO) {
    	log.info("查询文件组下文件: {}", fileGroupKeyQueryPO);
        return fileMapper.listByFileGroupKey(fileGroupKeyQueryPO);
    }
}
