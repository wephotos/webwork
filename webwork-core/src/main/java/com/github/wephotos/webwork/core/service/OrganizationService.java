package com.github.wephotos.webwork.core.service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.github.wephotos.webwork.core.entity.Organization;
import com.github.wephotos.webwork.core.entity.dto.DropSort;
import com.github.wephotos.webwork.core.mapper.OrganizationMapper;
import com.github.wephotos.webwork.http.EntityState;
import com.github.wephotos.webwork.security.entity.User;
import com.github.wephotos.webwork.utils.StringUtils;
import com.github.wephotos.webwork.utils.WebworkUtils;

/**
 * @author chengzi
 * @date 2021-01-25 16:46
 */
@Service
public class OrganizationService {
    @Resource
    private OrganizationMapper organizationMapper;

    /**
     * 保存节点
     * @param data 节点数据
     * @return 主键
     */
    public String add(Organization data) {
        data.setId(WebworkUtils.uuid());
        String parentId = data.getParentId();
        String maxCode = organizationMapper.findMaxCode(parentId);
        Organization parent = organizationMapper.selectById(parentId);
        if(parent == null) {
        	data.setCode(maxCode);
        }else {
        	data.setCode(parent.getCode().concat(maxCode));
        }
        organizationMapper.insert(data);
        return data.getId();
    }

    /**
     * 更新
     * @param organization
     * @return
     */
    public boolean update(Organization organization) {
        return SqlHelper.retBool(organizationMapper.updateById(organization));
    }

    /**
     * 删除 --逻辑删除
     * @param id 节点ID
     * @return 是否成功
     */
    public boolean delete(String id) {
        Organization org = new Organization();
        org.setId(id);
        org.setStatus(EntityState.DELETED.getValue());
        return SqlHelper.retBool(organizationMapper.updateById(org));
    }

    public Organization get(String id) {
        return organizationMapper.selectById(id);
    }

    /**
     * 查询下级组织机构
     *
     * @param parentId 父ID
     * @param user     当前用户
     * @return {@link Organization}
     */
    public List<Organization> children(String parentId, User user) {
        // 父节点为空，返回当前单位节点
        if (StringUtils.isBlank(parentId)) {
            Organization root = get(user.getGroupId());
            return Arrays.asList(root);
        }
        LambdaQueryWrapper<Organization> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Organization::getId, Organization::getName,
                Organization::getCode, Organization::getStatus,
                Organization::getType, Organization::getParentId);
        wrapper
        .eq(Organization::getParentId, parentId)
        .ne(Organization::getStatus, EntityState.DELETED.getValue());
        return organizationMapper.selectList(wrapper);
    }

    public boolean checkExistsName(Organization org) {
        LambdaQueryWrapper<Organization> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Organization::getId).eq(Organization::getName, org.getName());
        Organization result = organizationMapper.selectOne(wrapper);
        return Objects.isNull(result) || StringUtils.equals(result.getId(), org.getId());
    }

    public int dropSort(DropSort dropSort) {
        return organizationMapper.dropSort(dropSort);
    }
}
