package com.github.wephotos.webwork.file.mapper;

import com.github.wephotos.webwork.file.entity.WebworkFile;
import com.github.wephotos.webwork.file.entity.po.FileGroupKeyQueryPO;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 文件持久层接口
 *
 * @author TQ
 */
@Mapper
public interface FileMapper {
	
    /**
     * 新增文件
     *
     * @param file 文件数据
     * @return 文件ID
     */
    int insert(WebworkFile file);

    /**
     * 删除文件
     *
     * @param id 文件ID
     * @return 影响行数
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 查询文件记录
     *
     * @param id 文件ID
     * @return 文件记录
     */
    WebworkFile selectByPrimaryKey(Integer id);

    /**
     * 更新文件
     *
     * @param file 文件信息
     * @return 影响行数
     */
    int updateByPrimaryKeySelective(WebworkFile file);

    /**
     * 根据文件存储对象名获取存储记录
     * @param objectName 文件对象名
     * @return the {@link WebworkFile}
     */
    WebworkFile selectByObjectName(String objectName);
    
    /**
     * 获取文件组下的文件
     *
     * @param fileGroupKeyQueryPO 文件组信息
     * @return 文件列表
     */
    List<WebworkFile> listByFileGroupKey(FileGroupKeyQueryPO fileGroupKeyQueryPO);
}
