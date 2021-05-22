package com.github.wephotos.webwork.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.wephotos.webwork.core.entity.Dictionary;
import com.github.wephotos.webwork.http.Pageable;

/**
 * 数据字典持久层接口
 * @author TQ
 *
 */
@Mapper
public interface DictionaryMapper extends BaseMapper<Dictionary> {

	/**
	 * 获取当前最大排序号
	 * @param parentId 父ID
	 * @return 排序号
	 */
	int getMaxSort(String parentId);
	
	/**
	 * 分页数据总条数
	 * @param pageable 分页必要条件
	 * @return 总条数
	 */
	long pageCount(Pageable<Dictionary> pageable);
	
	/**
	 * 分页查询
	 * @param pageable 分页必要条件
	 * @return 分页数据
	 */
	List<Dictionary> pageList(Pageable<Dictionary> pageable);


}
