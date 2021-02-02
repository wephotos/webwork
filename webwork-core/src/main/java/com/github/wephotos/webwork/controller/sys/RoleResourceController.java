package com.github.wephotos.webwork.controller.sys;

import com.github.wephotos.webwork.service.sys.RoleResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色资源
 *
 * @author chengzi
 * @date 2021-01-25 16:55
 */
@RestController
@RequestMapping("/roleResource")
public class RoleResourceController {
    @Autowired
    private RoleResourceService roleResourceService;

}
