package com.github.wephotos.webwork.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.wephotos.webwork.user.entity.Organization;
import com.github.wephotos.webwork.user.entity.po.DropSortPO;

/**
 * @author chengzi
 * @date 2021-01-25 16:43
 */
@Mapper
public interface OrganizationMapper extends BaseMapper<Organization> {
	
	/**
	 * 获取最大的序号，最小返回0
	 * @param parentId 父ID
	 * @return 最大序号
	 */
	int getMaxSort(Integer parentId);
	
    /**
     * 获取最大编码
     *
     * @param parentId 父ID
     * @return 当前最大编码
     */
    String getMaxCode(Integer parentId);

    /**
     * 拖动排序
     * @param dropSort 拖动排序参数
     * @return 数据更新行数
     */
    int dropSort(DropSortPO dropSort);

}
