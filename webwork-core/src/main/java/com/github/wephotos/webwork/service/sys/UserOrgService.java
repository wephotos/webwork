package com.github.wephotos.webwork.service.sys;

import com.github.wephotos.webwork.mapper.sys.UserOrgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chengzi
 * @date 2021-01-25 16:46
 */
@Service
public class UserOrgService {
    @Autowired
    private UserOrgMapper userOrgMapper;

}
