package com.github.wephotos.webwork.core.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.wephotos.webwork.core.entity.RoleResource;
import com.github.wephotos.webwork.core.entity.dto.RoleResoDto;
import com.github.wephotos.webwork.core.mapper.RoleResourceMapper;
import com.github.wephotos.webwork.utils.WebworkUtils;

/**
 * @author chengzi
 * @date 2021-01-25 16:46
 */
@Service
public class RoleResourceService {
	
    @Resource
    private RoleResourceMapper roleResourceMapper;
    
    /**
     * 添加角色资源关系
     * @param record 角色资源对象
     * @return 新增记录主键
     */
    public String add(RoleResource record) {
    	record.setId(WebworkUtils.uuid());
    	roleResourceMapper.insert(record);
    	return record.getId();
    }

    /**
     * 保存角色资源信息
     * @param record 数据对象
     * @return 保存成功返回true
     */
    public void save(RoleResoDto record) {
    	deleteByRoleId(record.getRoleId());
    	List<String> resList = record.getResources();
    	if(resList == null || resList.isEmpty()) {
    		return;
    	}
    	RoleResource entity = new RoleResource();
    	resList.forEach(resourceId -> {
    		entity.setId(WebworkUtils.uuid());
    		entity.setRoleId(record.getRoleId());
    		entity.setResourceId(resourceId);
    		roleResourceMapper.insert(entity);
    	});
    }
    
    /**
     * 删除角色资源
     * @param roleId 角色ID
     * @return 删除成功返回true
     */
    public boolean deleteByRoleId(String roleId) {
    	LambdaQueryWrapper<RoleResource> query = new LambdaQueryWrapper<>();
    	query.eq(RoleResource::getRoleId, roleId);
    	return roleResourceMapper.delete(query) > 0;
    }
    
    /**
     * 删除角色资源关联关系
     * @param id 主键
     * @return 成功返回true
     */
    public boolean deleteById(String id) {
    	return roleResourceMapper.deleteById(id) ==1;
    }
}
