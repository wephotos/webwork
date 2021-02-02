package com.github.wephotos.webwork.service;

import com.github.wephotos.webwork.mapper.UserOrgMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author chengzi
 * @date 2021-01-25 16:46
 */
@Service
public class UserOrgService {
    @Resource
    private UserOrgMapper userOrgMapper;

}
