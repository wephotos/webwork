package com.github.wephotos.webwork.user.service;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.wephotos.webwork.logging.LoggerFactory;
import com.github.wephotos.webwork.user.entity.RoleResource;
import com.github.wephotos.webwork.user.entity.po.RoleResourcesPO;
import com.github.wephotos.webwork.user.mapper.RoleResourceMapper;
import com.github.wephotos.webwork.utils.WebworkUtils;

/**
 * @author chengzi
 * @date 2021-01-25 16:46
 */
@Service
public class RoleResourceService {

	private static final Logger log = LoggerFactory.getLogger(RoleResourceService.class);
	
    @Resource
    private RoleResourceMapper roleResourceMapper;
    
    /**
     * 添加角色资源关系
     * @param record 角色资源对象
     * @return 新增记录主键
     */
    public Integer add(RoleResource record) {
    	log.info("添加(单个)角色资源: {}", record);
    	record.setCreateTime(WebworkUtils.nowTime());
    	roleResourceMapper.insert(record);
    	return record.getId();
    }

    /**
     * 保存角色资源信息
     * @param po 授权信息
     * @return 保存成功返回true
     */
    public void save(RoleResourcesPO po) {
    	log.info("保存(全量)角色资源: {}", po);
    	deleteByRoleId(po.getRoleId());
    	List<Integer> resList = po.getResources();
    	if(resList == null || resList.isEmpty()) {
    		return;
    	}
    	RoleResource entity = new RoleResource();
    	entity.setCreateUser(po.getCreateUser());
    	entity.setCreateTime(WebworkUtils.nowTime());
    	resList.forEach(resourceId -> {
    		entity.setRoleId(po.getRoleId());
    		entity.setResourceId(resourceId);
    		roleResourceMapper.insert(entity);
    	});
    }
    
    /**
     * 删除角色资源
     * @param roleId 角色ID
     * @return 删除成功返回true
     */
    public boolean deleteByRoleId(Integer roleId) {
    	log.info("删除角色资源, roleId = {}", roleId);
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
    	log.info("删除角色资源, id = {}", id);
    	return roleResourceMapper.deleteById(id) == 1;
    }
}
