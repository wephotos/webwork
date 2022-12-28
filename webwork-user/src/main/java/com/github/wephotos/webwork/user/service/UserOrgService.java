package com.github.wephotos.webwork.user.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wephotos.webwork.user.entity.UserOrg;
import com.github.wephotos.webwork.user.mapper.UserOrgMapper;

/**
 * @author chengzi
 * @date 2021-01-25 16:46
 */
@Service
public class UserOrgService extends ServiceImpl<UserOrgMapper, UserOrg> {
	
    @Resource
    private UserOrgMapper userOrgMapper;

    /**
	 * 获取用户组织信息
	 * @param userId 用户ID
	 * @return 用户组织信息
	 */
    public List<UserOrg> listOrgByUserId(Integer userId){
    	LambdaQueryWrapper<UserOrg> wrapper = new LambdaQueryWrapper<>();
    	// 检索字段
    	wrapper.select(UserOrg::getId, UserOrg::getDeptId, 
    			UserOrg::getOrgId, UserOrg::getMainDept, UserOrg::getUserSort);
    	
    	// 根据用户ID查询
		wrapper.eq(UserOrg::getUserId, userId);
    	
    	return userOrgMapper.selectList(wrapper);
    }
}
