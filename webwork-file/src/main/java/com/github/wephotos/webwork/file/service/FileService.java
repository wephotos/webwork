package com.github.wephotos.webwork.file.service;


import com.github.wephotos.webwork.file.entity.UploadResult;
import com.github.wephotos.webwork.file.entity.WebworkFile;
import com.github.wephotos.webwork.file.mapper.FileMapper;
import com.github.wephotos.webwork.file.stor.FileStor;
import com.github.wephotos.webwork.http.EntityState;
import com.github.wephotos.webwork.utils.FilenameUtils;
import com.github.wephotos.webwork.utils.StringUtils;
import com.github.wephotos.webwork.utils.WebworkUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

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
     *
     * @param file 附件
     * @return {@link UploadResult}
     * @throws IOException IO异常
     */
    public UploadResult upload(WebworkFile file) throws IOException {
        if (StringUtils.isBlank(file.getOwner())) {
            throw new IllegalArgumentException("附件关联外键不能为空");
        }
        String objectNameParam = file.getName();
        if (StringUtils.isNotBlank(file.getObjectName())) {
            String extension = FilenameUtils.getExtension(file.getName());
            objectNameParam = file.getObjectName() + "." + extension;
        }
        String objectName = fileStor.getNewObjectName(objectNameParam);
        file.setObjectName(objectName);
        fileStor.storage(file);
        file.setId(WebworkUtils.uuid());
        file.setStatus(EntityState.ENABLED.getValue());
        file.setCreateTime(WebworkUtils.timestamp());
        fileMapper.insert(file);
        return UploadResult.builder()
                           .id(file.getId())
                           .name(file.getName())
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
    public int logicDelete(String id) {
        WebworkFile file = new WebworkFile();
        file.setId(id);
        file.setStatus(EntityState.DISABLED.getValue());
        return fileMapper.updateByPrimaryKeySelective(file);
    }
}
