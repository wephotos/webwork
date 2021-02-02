package com.github.wephotos.webwork.controller.sys;

import com.github.wephotos.webwork.service.sys.UserOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户组织
 *
 * @author chengzi
 * @date 2021-01-25 16:55
 */
@RestController
@RequestMapping("/userOrg")
public class UserOrgController {
    @Autowired
    private UserOrgService userOrgService;
}
