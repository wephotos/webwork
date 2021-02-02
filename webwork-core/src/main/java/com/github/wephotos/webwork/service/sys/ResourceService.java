package com.github.wephotos.webwork.service.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.github.wephotos.webwork.entity.sys.Resource;
import com.github.wephotos.webwork.mapper.sys.ResourceMapper;
import com.github.wephotos.webwork.mapper.sys.RoleResourceMapper;
import com.github.wephotos.webwork.util.Errors;
import com.github.wephotos.webwork.util.ValidationUtil;
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
public class ResourceService {
    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private RoleResourceMapper roleResourceMapper;

    public boolean create(Resource resource) {
        ValidationUtil.isTrue(Errors.RESOURCE_NAME_EXIST, checkResourceNameUnique(resource));
        ValidationUtil.isTrue(Errors.RESOURCE_CODE_EXIST, checkResourceCodeUnique(resource));
        resource.setId(WebWorkUtil.uuid());
        resource.setStatus(1);
        return SqlHelper.retBool(resourceMapper.insert(resource));
    }

    public boolean update(Resource resource) {
        ValidationUtil.isTrue(Errors.RESOURCE_NAME_EXIST, checkResourceNameUnique(resource));
        ValidationUtil.isTrue(Errors.RESOURCE_CODE_EXIST, checkResourceCodeUnique(resource));
        return SqlHelper.retBool(resourceMapper.updateById(resource));
    }

    public Resource get(String id) {
        QueryWrapper<Resource> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Resource::getId, id);
        return resourceMapper.selectOne(wrapper);
    }

    public boolean checkResourceNameUnique(Resource resource) {
        QueryWrapper<Resource> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Resource::getName, resource.getName());
        Resource result = resourceMapper.selectOne(wrapper);
        return Objects.isNull(result) || StringUtils.equals(result.getId(), resource.getId());
    }

    public boolean checkResourceCodeUnique(Resource resource) {
        QueryWrapper<Resource> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Resource::getCode, resource.getCode());
        Resource result = resourceMapper.selectOne(wrapper);
        return Objects.isNull(result) || StringUtils.equals(result.getId(), resource.getId());
    }
}
