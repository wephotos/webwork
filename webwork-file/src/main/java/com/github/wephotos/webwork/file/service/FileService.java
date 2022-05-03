package com.github.wephotos.webwork.file.service;


import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.wephotos.webwork.file.entity.UploadResult;
import com.github.wephotos.webwork.file.entity.WebworkFile;
import com.github.wephotos.webwork.file.mapper.FileMapper;
import com.github.wephotos.webwork.file.stor.FileStor;
import com.github.wephotos.webwork.schema.entity.EntityState;
import com.github.wephotos.webwork.utils.StringUtils;
import com.github.wephotos.webwork.utils.WebworkUtils;

/**
 * @author chengzi
 * @date 2021-03-08 19:59
 */
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
     *
     * @param file 附件
     * @return {@link UploadResult}
     * @throws IOException IO异常
     */
    public UploadResult upload(WebworkFile file) throws IOException {
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
        	file.setStatus(EntityState.ENABLED.getValue());
        	file.setCreateTime(WebworkUtils.timestamp());
        	fileMapper.insert(file);
        }
        //存储文件
        fileStor.storage(file);
        // 返回主键 文件名 存储对象名
        return UploadResult.builder()
                           .id(file.getId())
                           .name(file.getName())
                           .owner(file.getOwner())
                           .objectName(file.getObjectName()).build();
    }

    /**
     * 删除
     *
     * @param id 附件id
     * @return {@link java.lang.Integer} 大于0表示删除成功
     * @throws IOException IO异常
     */
    public int deleteByPrimaryKey(String id) throws IOException {
        WebworkFile file = fileMapper.selectByPrimaryKey(id);
        fileStor.delete(file.getObjectName());
        return fileMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取文件，包含文件流
     *
     * @param id 附件id
     * @return {@link WebworkFile}
     * @throws IOException IO异常
     */
    public WebworkFile getFile(String id) throws IOException {
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
        WebworkFile file = fileMapper.selectByObjectName(objectName);
        file.setInputStream(fileStor.get(file.getObjectName()));
        return file;
    }

    /**
     * 获取文件
     *
     * @param owner 附件归属者
     * @return {@link List<WebworkFile>}
     */
    public List<WebworkFile> list(String owner) {
        return fileMapper.list(owner);
    }

    /**
     * 逻辑删除
     *
     * @param id id
     */
    public int deleteSoftById(Integer id) {
        WebworkFile file = new WebworkFile();
        file.setId(id);
        file.setStatus(EntityState.DELETED.getValue());
        return fileMapper.updateByPrimaryKeySelective(file);
    }
}
