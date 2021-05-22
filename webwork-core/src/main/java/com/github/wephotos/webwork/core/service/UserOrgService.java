package com.github.wephotos.webwork.core.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.wephotos.webwork.core.mapper.UserOrgMapper;

/**
 * @author chengzi
 * @date 2021-01-25 16:46
 */
@Service
public class UserOrgService {
    @Resource
    private UserOrgMapper userOrgMapper;

}
