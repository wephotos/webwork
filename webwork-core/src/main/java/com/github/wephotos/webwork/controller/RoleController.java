package com.github.wephotos.webwork.controller;

import com.github.wephotos.webwork.entity.Role;
import com.github.wephotos.webwork.service.RoleService;
import com.github.wephotos.webwork.utils.Errors;
import com.github.wephotos.webwork.utils.RestObject;
import com.github.wephotos.webwork.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 角色
 *
 * @author chengzi
 * @date 2021-01-25 16:55
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * 新增角色
     *
     * @param role role
     * @return RestObject
     */
    @PostMapping("/create")
    public RestObject create(@RequestBody Role role) {
        ValidationUtil.isTrue(Errors.ROLE_NAME_EXIST, roleService.checkRoleNameUnique(role));
        ValidationUtil.isTrue(Errors.ROLE_CODE_EXIST, roleService.checkRoleCodeUnique(role));
        boolean result = roleService.create(role);
        return RestObject.builder().data(result).build();
    }

    /**
     * 修改角色
     *
     * @param role role
     * @return RestObject
     */
    @PostMapping("/update")
    public RestObject update(@RequestBody Role role) {
        ValidationUtil.isTrue(Errors.ROLE_NAME_EXIST, roleService.checkRoleNameUnique(role));
        ValidationUtil.isTrue(Errors.ROLE_CODE_EXIST, roleService.checkRoleCodeUnique(role));
        boolean result = roleService.update(role);
        return RestObject.builder().data(result).build();
    }

    /**
     * 禁用角色
     *
     * @param id 角色id
     * @return RestObject
     */
    @PostMapping("/disable/{id}")
    public RestObject disable(@PathVariable String id) {
        boolean result = roleService.disable(id);
        return RestObject.builder().data(result).build();
    }

    /**
     * 启用角色
     *
     * @param id 角色id
     * @return RestObject
     */
    @PostMapping("/enable/{id}")
    public RestObject enable(@PathVariable String id) {
        boolean result = roleService.enable(id);
        return RestObject.builder().data(result).build();
    }

    /**
     * 根据id获取角色信息
     *
     * @param id 角色id
     * @return RestObject
     */
    @GetMapping("/get/{id}")
    public RestObject get(@PathVariable String id) {
        Role role = roleService.get(id);
        return RestObject.builder().data(role).build();
    }

    /**
     * 校验角色名称
     *
     * @param roleName 角色名称
     * @return RestObject
     */
    @GetMapping("/checkRoleNameUnique")
    public RestObject checkRoleNameUnique(@RequestParam String roleName) {
        Role role = new Role();
        role.setName(roleName);
        boolean bool = roleService.checkRoleNameUnique(role);
        return RestObject.builder().data(bool).build();
    }

    /**
     * 校验角色Code
     *
     * @param code 角色code
     * @return RestObject
     */
    @GetMapping("/checkRoleCodeUnique")
    public RestObject checkRoleCodeUnique(@RequestParam String code) {
        Role role = new Role();
        role.setCode(code);
        boolean bool = roleService.checkRoleCodeUnique(role);
        return RestObject.builder().data(bool).build();
    }
}
