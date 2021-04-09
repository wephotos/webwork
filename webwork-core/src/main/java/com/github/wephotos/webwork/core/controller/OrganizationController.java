package com.github.wephotos.webwork.core.controller;


import com.github.wephotos.webwork.core.entity.Organization;
import com.github.wephotos.webwork.core.service.OrganizationService;
import com.github.wephotos.webwork.http.RestObject;
import com.github.wephotos.webwork.security.entity.User;
import com.github.wephotos.webwork.security.storage.SessionUserStorage;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 组织
 *
 * @author chengzi
 * @date 2021-01-25 16:55
 */
@RestController
@RequestMapping("/organization")
public class OrganizationController {
    @Resource
    private OrganizationService organizationService;

    @PostMapping("/save")
    public RestObject create(@RequestBody Organization organization) {
        boolean result = organizationService.save(organization);
        return RestObject.builder().data(result).build();
    }

    @PostMapping("/update")
    public RestObject update(@RequestBody Organization organization) {
        boolean result = organizationService.update(organization);
        return RestObject.builder().data(result).build();
    }

    @PostMapping("/disable/{id}")
    public RestObject disable(@PathVariable String id) {
        boolean result = organizationService.disable(id);
        return RestObject.builder().data(result).build();
    }

    @PostMapping("/enable/{id}")
    public RestObject enable(@PathVariable String id) {
        boolean result = organizationService.enable(id);
        return RestObject.builder().data(result).build();
    }

    /**
     * 根据id获取组织
     *
     * @param id id
     * @return RestObject
     */
    @GetMapping("/get/{id}")
    public RestObject get(@PathVariable String id) {
        Organization org = organizationService.get(id);
        return RestObject.builder().data(org).build();
    }

    /**
     * 查询组织下组织或部门
     *
     * @param parentId 组织/部门id
     * @param type     类型：1：组织，2：部门
     * @return RestObject
     */
    @PostMapping("/children")
    public RestObject children(String parentId, Integer type, HttpSession session) {
        User sessionUser = SessionUserStorage.get(session);
        List<Organization> organizationList = organizationService.children(parentId, type, sessionUser);
        return RestObject.builder().data(organizationList).build();
    }

    @GetMapping("/check-exists-name")
    public RestObject checkExistsName(@RequestParam String name) {
        Organization org = new Organization();
        org.setName(name);
        boolean bool = organizationService.checkExistsName(org);
        return RestObject.builder().data(bool).build();
    }
}
