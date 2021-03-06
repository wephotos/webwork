package com.github.wephotos.webwork.core.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.wephotos.webwork.core.entity.RoleUser;
import com.github.wephotos.webwork.core.mapper.RoleUserMapper;
import com.github.wephotos.webwork.utils.WebworkUtils;

/**
 * @author chengzi
 * @date 2021-01-25 16:46
 */
@Service
public class RoleUserService {
	
    @Resource
    private RoleUserMapper roleUserMapper;

    /**
     * 添加角色成员
     * @param record 角色成员
     * @return 添加数据主键
     */
    public String add(RoleUser record) {
    	record.setId(WebworkUtils.uuid());
    	record.setCreateTime(WebworkUtils.timestamp());
    	roleUserMapper.insert(record);
    	return record.getId();
    }

    /**
     * 删除角色用户
     * @param id 主键
     * @return 删除成功返回true
     */
    public boolean deleteById(String id) {
        return roleUserMapper.deleteById(id) == 1;
    }

    /**
     * 查询角色成员
     * @param roleId 角色ID
     * @return 角色成员集合
     */
	public List<RoleUser> listByRoleId(String roleId) {
		LambdaQueryWrapper<RoleUser> query = new LambdaQueryWrapper<>();
		query.eq(RoleUser::getRoleId, roleId);
		return roleUserMapper.selectList(query);
	}
}
