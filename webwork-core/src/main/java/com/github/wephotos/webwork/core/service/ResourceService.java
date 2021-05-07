package com.github.wephotos.webwork.core.service;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.github.wephotos.webwork.core.entity.Resource;
import com.github.wephotos.webwork.core.mapper.ResourceMapper;
import com.github.wephotos.webwork.utils.WebworkUtils;

/**
 * @author chengzi
 * @date 2021-01-25 16:46
 */
@Service
public class ResourceService {
    @javax.annotation.Resource
    private ResourceMapper resourceMapper;

    public boolean save(Resource resource) {
        String maxCode = resourceMapper.findMaxCode(resource.getParentId());
        resource.setId(WebworkUtils.uuid());
        resource.setStatus(1);
        if (StringUtils.isNotBlank(resource.getParentId())) {
            Resource parent = resourceMapper.selectById(resource.getParentId());
            resource.setCode(parent.getCode().concat(maxCode));
        } else {
            resource.setCode(maxCode);
        }
        return SqlHelper.retBool(resourceMapper.insert(resource));
    }

    public boolean update(Resource resource) {
        return SqlHelper.retBool(resourceMapper.updateById(resource));
    }

    public Resource get(String id) {
        LambdaQueryWrapper<Resource> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Resource::getId).eq(Resource::getId, id);
        return resourceMapper.selectOne(wrapper);
    }

    public boolean checkExistsName(Resource resource) {
        LambdaQueryWrapper<Resource> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Resource::getId).eq(Resource::getName, resource.getName());
        com.github.wephotos.webwork.core.entity.Resource result = resourceMapper.selectOne(wrapper);
        return Objects.isNull(result) || StringUtils.equals(result.getId(), resource.getId());
    }

    public boolean checkExistsPermission(Resource resource) {
        LambdaQueryWrapper<Resource> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Resource::getId).eq(Resource::getCode, resource.getCode());
        com.github.wephotos.webwork.core.entity.Resource result = resourceMapper.selectOne(wrapper);
        return Objects.isNull(result) || StringUtils.equals(result.getId(), resource.getId());
    }
}
