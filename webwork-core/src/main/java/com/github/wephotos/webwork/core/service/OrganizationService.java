package com.github.wephotos.webwork.core.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.github.wephotos.webwork.core.entity.Organization;
import com.github.wephotos.webwork.core.mapper.OrganizationMapper;
import com.github.wephotos.webwork.core.utils.WebWorkUtil;
import com.github.wephotos.webwork.http.EntityState;
import com.github.wephotos.webwork.security.entity.User;
import com.github.wephotos.webwork.utils.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

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

    public List<Organization> children(String parentId, Integer type, User sessionUser) {
        LambdaQueryWrapper<Organization> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Organization::getId, Organization::getName,
                Organization::getCode, Organization::getStatus,
                Organization::getType, Organization::getParentId);
        String deptId = sessionUser.getDeptId();
        // 没有传parentId获取当前登录用户的部门id
        if (StringUtils.isBlank(parentId)) {
            parentId = deptId;
        }
        wrapper.eq(Organization::getParentId, parentId);
        if (null != type) {
            wrapper.eq(Organization::getType, type);
        }
        return organizationMapper.selectList(wrapper);
    }

    public boolean checkExistsName(Organization org) {
        LambdaQueryWrapper<Organization> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Organization::getId).eq(Organization::getName, org.getName());
        Organization result = organizationMapper.selectOne(wrapper);
        return Objects.isNull(result) || StringUtils.equals(result.getId(), org.getId());
    }
}
