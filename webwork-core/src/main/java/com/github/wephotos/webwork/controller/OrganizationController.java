package com.github.wephotos.webwork.controller;


import com.github.wephotos.webwork.entity.Organization;
import com.github.wephotos.webwork.service.OrganizationService;
import com.github.wephotos.webwork.utils.RestObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 组织
 *
 * @author chengzi
 * @date 2021-01-25 16:55
 */
@RestController
@RequestMapping("/organization")
public class OrganizationController {
    @Autowired
    private OrganizationService organizationService;

    @PostMapping("/create")
    public RestObject create(@RequestBody Organization organization) {
        boolean result = organizationService.create(organization);
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


    @GetMapping("/checkOrgNameUnique")
    public RestObject checkOrgNameUnique(@RequestParam String name) {
        Organization org = new Organization();
        org.setName(name);
        boolean bool = organizationService.checkOrgNameUnique(org);
        return RestObject.builder().data(bool).build();
    }
}
