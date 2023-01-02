package com.github.wephotos.webwork.docs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.wephotos.webwork.docs.entity.Document;
import com.github.wephotos.webwork.docs.entity.po.DocQueryPO;
import com.github.wephotos.webwork.schema.entity.Pageable;

/**
 * 文档持久层接口
 * @author TQ
 *
 */
@Mapper
public interface DocumentMapper extends BaseMapper<Document> {

	/**
	 * 文档总条数
	 * @param pageable 分页条件
	 * @return 总条数
	 */
	long pageCount(Pageable<DocQueryPO> pageable);

	/**
	 * 文档分页查询
	 * @param pageable 分页条件
	 * @return 配置数据集合
	 */
	List<Document> pageList(Pageable<DocQueryPO> pageable);

}
