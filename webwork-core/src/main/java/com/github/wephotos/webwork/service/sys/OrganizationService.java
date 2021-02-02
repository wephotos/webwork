package com.github.wephotos.webwork.service.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.github.wephotos.webwork.entity.sys.OrgState;
import com.github.wephotos.webwork.entity.sys.Organization;
import com.github.wephotos.webwork.mapper.sys.OrganizationMapper;
import com.github.wephotos.webwork.mapper.sys.UserOrgMapper;
import com.github.wephotos.webwork.util.WebWorkUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author chengzi
 * @date 2021-01-25 16:46
 */
@Service
public class OrganizationService {
    @Autowired
    private OrganizationMapper organizationMapper;
    @Autowired
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
        QueryWrapper<Organization> wrapper = new QueryWrapper<>();
        wrapper.select("id").lambda().eq(Organization::getName, org.getName());
        Organization result = organizationMapper.selectOne(wrapper);
        return Objects.isNull(result) || StringUtils.equals(result.getId(), org.getId());
    }
}
