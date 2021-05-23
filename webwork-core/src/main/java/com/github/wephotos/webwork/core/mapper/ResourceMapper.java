package com.github.wephotos.webwork.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.wephotos.webwork.core.entity.Resource;
import com.github.wephotos.webwork.core.entity.query.ResQuery;
import com.github.wephotos.webwork.http.Pageable;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;


/**
 * @author chengzi
 * @date 2021-01-25 16:43
 */
@Mapper
public interface ResourceMapper extends BaseMapper<Resource> {
	
	/**
	 * 获取最大的应用编码
	 * @return 最大的应用编码
	 */
	String getMaxCodeApp();
    /**
     * 获取最大模块或功能编码
     *
     * @param parentId 父ID
     * @return 当前最大编码
     */
    String getMaxCode(String parentId);
    /**
     * 获取当前最大排序号
     * @param parentId 父ID
     * @return 序号
     */
    int getMaxSort(String parentId);
    /**
     * 获取分页数据总条数
     * @param pageable 分页条件
     * @return 总条数
     */
    long pageCount(Pageable<ResQuery> pageable);
    /**
     * 获取分页数据
     * @param pageable 分页条件
     * @return 分页数据
     */
	List<Resource> pageList(Pageable<ResQuery> pageable);
	
	/**
	 * 获取角色下的资源集合
	 * @param roleId 角色ID
	 * @return 资源集合 {@link Resource}
	 */
	List<Resource> listByRoleId(String roleId);


}
