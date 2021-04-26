package com.github.wephotos.webwork.core.service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.github.wephotos.webwork.core.entity.Organization;
import com.github.wephotos.webwork.core.mapper.OrganizationMapper;
import com.github.wephotos.webwork.core.utils.WebWorkUtil;
import com.github.wephotos.webwork.http.EntityState;
import com.github.wephotos.webwork.security.entity.User;
import com.github.wephotos.webwork.utils.StringUtils;

/**
 * @author chengzi
 * @date 2021-01-25 16:46
 */
@Service
public class OrganizationService {
    @Resource
    private OrganizationMapper organizationMapper;

    public boolean save(Organization organization) {
        String maxCode = organizationMapper.findMaxCode(organization.getParentId());
        organization.setId(WebWorkUtil.uuid());
        organization.setStatus(1);
        if (StringUtils.isNotBlank(organization.getParentId())) {
            Organization parent = organizationMapper.selectById(organization.getParentId());
            organization.setCode(parent.getCode().concat(maxCode));
        } else {
            organization.setCode(maxCode);
        }
        return SqlHelper.retBool(organizationMapper.insert(organization));
    }

    public boolean update(Organization organization) {
        return SqlHelper.retBool(organizationMapper.updateById(organization));
    }

    public boolean disable(String id) {
        Organization org = new Organization();
        org.setStatus(EntityState.DISABLED.getValue());
        org.setId(id);
        return SqlHelper.retBool(organizationMapper.updateById(org));
    }

    public boolean enable(String id) {
        Organization org = new Organization();
        org.setStatus(EntityState.ENABLED.getValue());
        org.setId(id);
        return SqlHelper.retBool(organizationMapper.updateById(org));
    }

    public Organization get(String id) {
        return organizationMapper.selectById(id);
    }

    /**
     * 查询下级组织机构
     * @param parentId 父ID
     * @param user 当前用户
     * @return {@link Organization}
     */
    public List<Organization> children(String parentId, User user) {
    	// 父节点为空，返回当前单位节点
    	if(StringUtils.isBlank(parentId)) {
    		Organization root = get(user.getGroupId());
    		return Arrays.asList(root);
    	}
        LambdaQueryWrapper<Organization> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Organization::getId, Organization::getName,
                Organization::getCode, Organization::getStatus,
                Organization::getType, Organization::getParentId);
        wrapper.eq(Organization::getParentId, parentId);
        return organizationMapper.selectList(wrapper);
    }

    public boolean checkExistsName(Organization org) {
        LambdaQueryWrapper<Organization> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Organization::getId).eq(Organization::getName, org.getName());
        Organization result = organizationMapper.selectOne(wrapper);
        return Objects.isNull(result) || StringUtils.equals(result.getId(), org.getId());
    }
}
