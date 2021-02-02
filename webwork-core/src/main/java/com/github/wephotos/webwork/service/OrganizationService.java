package com.github.wephotos.webwork.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.github.wephotos.webwork.entity.OrgState;
import com.github.wephotos.webwork.entity.Organization;
import com.github.wephotos.webwork.mapper.OrganizationMapper;
import com.github.wephotos.webwork.mapper.UserOrgMapper;
import com.github.wephotos.webwork.utils.WebWorkUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author chengzi
 * @date 2021-01-25 16:46
 */
@Service
public class OrganizationService {
    @Resource
    private OrganizationMapper organizationMapper;
    @Resource
    private UserOrgMapper userOrgMapper;

    public boolean create(Organization organization) {
        organization.setId(WebWorkUtil.uuid());
        organization.setStatus(1);
        return SqlHelper.retBool(organizationMapper.insert(organization));

    }

    public boolean update(Organization organization) {
        return SqlHelper.retBool(organizationMapper.updateById(organization));
    }

    public boolean disable(String id) {
        Organization org = new Organization();
        org.setStatus(OrgState.DISABLED.getValue());
        org.setId(id);
        return SqlHelper.retBool(organizationMapper.updateById(org));
    }

    public boolean enable(String id) {
        Organization org = new Organization();
        org.setStatus(OrgState.ENABLED.getValue());
        org.setId(id);
        return SqlHelper.retBool(organizationMapper.updateById(org));
    }

    public Organization get(String id) {
        return organizationMapper.selectById(id);
    }

    public boolean checkOrgNameUnique(Organization org) {
        LambdaQueryWrapper<Organization> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Organization::getId).eq(Organization::getName, org.getName());
        Organization result = organizationMapper.selectOne(wrapper);
        return Objects.isNull(result) || StringUtils.equals(result.getId(), org.getId());
    }
}
