package com.github.wephotos.webwork.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.wephotos.webwork.schema.entity.Pageable;
import com.github.wephotos.webwork.user.client.entity.po.UserResoQueryPO;
import com.github.wephotos.webwork.user.entity.Resource;
import com.github.wephotos.webwork.user.entity.po.ResourceQueryPO;
import com.github.wephotos.webwork.user.entity.vo.ResoVO;


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
     * @param parentId 父节点ID
     * @param parentType 父节点类型
     * @return 当前最大编码
     */
    String getMaxCode(@Param("parentId") Integer parentId, @Param("parentType") Integer parentType);
    
    /**
     * 获取当前最大排序号
     * @param parentId 父节点ID
     * @param parentType 父节点类型
     * @return 序号
     */
    int getMaxSort(@Param("parentId") Integer parentId, @Param("parentType") Integer parentType);
    
    /**
     * 获取分页数据总条数
     * @param pageable 分页条件
     * @return 总条数
     */
    long pageCount(Pageable<ResourceQueryPO> pageable);
    
    /**
     * 获取分页数据
     * @param pageable 分页条件
     * @return 分页数据
     */
	List<Resource> pageList(Pageable<ResourceQueryPO> pageable);
	
	/**
	 * 获取角色下的资源集合
	 * @param roleId 角色ID
	 * @return 资源集合 {@link Resource}
	 */
	List<Resource> listByRoleId(Integer roleId);
	
	/**
	 * 根据条件进行资源查询
	 * @param po 查询参数
	 * @return 资源集合
	 */
	List<ResoVO> listQueryResource(UserResoQueryPO po);

}
