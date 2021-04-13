package com.github.wephotos.webwork.file.mapper;

import com.github.wephotos.webwork.file.entity.WebworkFile;
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
     * 新增
     *
     * @param file file
     * @return int
     */
    int insert(WebworkFile file);

    /**
     * 删除
     *
     * @param id id
     * @return int
     */
    int deleteByPrimaryKey(String id);

    /**
     * 查询
     *
     * @param id id
     * @return WebworkFile
     */
    WebworkFile selectByPrimaryKey(String id);

    /**
     * 更新
     *
     * @param file file
     * @return int
     */
    int updateByPrimaryKeySelective(WebworkFile file);

    /**
     * 获取所属附件
     *
     * @param owner 附件主体
     * @return 附件集合
     */
    List<WebworkFile> list(String owner);

    /**
     * 根据文件存储对象名获取存储记录
     * @param objectName 文件对象名
     * @return the {@link WebworkFile}
     */
	WebworkFile selectByObjectName(String objectName);
}
