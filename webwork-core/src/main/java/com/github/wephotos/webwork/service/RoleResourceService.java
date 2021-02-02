package com.github.wephotos.webwork.service;

import com.github.wephotos.webwork.mapper.RoleResourceMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author chengzi
 * @date 2021-01-25 16:46
 */
@Service
public class RoleResourceService {
    @Resource
    private RoleResourceMapper roleResourceMapper;

}
