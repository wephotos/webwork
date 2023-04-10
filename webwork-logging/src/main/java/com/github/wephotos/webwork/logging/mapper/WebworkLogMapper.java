package com.github.wephotos.webwork.logging.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.wephotos.webwork.logging.entity.WebworkLog;
import com.github.wephotos.webwork.logging.entity.po.LogQueryPO;
import com.github.wephotos.webwork.schema.entity.Pageable;

/**
 * @author xc
 * @date 2021-04-28 21:49
 */
@Mapper
public interface WebworkLogMapper {
    /**
     * 新增
     *
     * @param log log
     * @return int
     */
    int insert(WebworkLog log);

    /**
     * 批量插入
     * 
     * @param logs 日志集合
     * @return 影响行数
     */
	int insertBatch(@Param("list") List<WebworkLog> logs);
	
    /**
     * 查询分页总条数
     * @param pageable 分页条件
     * @return 总数
     */
	int pageCount(Pageable<LogQueryPO> pageable);

	/**
	 * 查询分页列表数据
	 * @param pageable 列表数据
	 * @return 列表数据
	 */
	List<WebworkLog> pageList(Pageable<LogQueryPO> pageable);

}
