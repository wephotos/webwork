package com.github.wephotos.webwork.config.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.wephotos.webwork.config.entity.Config;
import com.github.wephotos.webwork.schema.entity.Pageable;

/**
 * 系统配置持久层接口
 * @author TQ
 *
 */
@Mapper
public interface ConfigMapper extends BaseMapper<Config> {
	
	/**
	 * 获取当前最大排序号
	 * @return 排序号
	 */
	int getMaxSort();

	/**
	 * 记录总条数
	 * @param pageable 分页条件
	 * @return 总条数
	 */
	long pageCount(Pageable<Config> pageable);

	/**
	 * 配置分页查询
	 * @param pageable 分页条件
	 * @return 配置数据集合
	 */
	List<Config> pageList(Pageable<Config> pageable);

}
