package com.github.wephotos.webwork.logging.mapper;

import com.github.wephotos.webwork.http.Pageable;
import com.github.wephotos.webwork.logging.entity.WebworkLog;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

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
     * 查询分页总条数
     * @param pageable 分页条件
     * @return 总数
     */
	int pageCount(Pageable<WebworkLog> pageable);

	/**
	 * 查询分页列表数据
	 * @param pageable 列表数据
	 * @return 列表数据
	 */
	List<WebworkLog> pageList(Pageable<WebworkLog> pageable);
}
