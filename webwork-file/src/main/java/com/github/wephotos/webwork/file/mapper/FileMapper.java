package com.github.wephotos.webwork.file.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.github.wephotos.webwork.file.entity.WebworkFile;

/**
 * 文件持久层接口
 * @author TQ
 *
 */
@Mapper
public interface FileMapper {
	/**
	 * 新增
	 * @param record
	 * @return
	 */
	int insert(WebworkFile file);
	/**
	 * 删除
	 * @param id
	 * @return
	 */
    int deleteByPrimaryKey(String id);
    /**
     * 查询
     * @param id
     * @return
     */
    WebworkFile selectByPrimaryKey(String id);

    /**
     * 更新
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(WebworkFile file);
	/**
	 * 获取所属附件
	 * @param owner 附件主体
	 * @return 附件集合
	 */
	List<WebworkFile> list(String owner);
}
