package com.github.wephotos.webwork.core.controller;

import com.github.wephotos.webwork.core.entity.Role;
import com.github.wephotos.webwork.core.entity.dto.RoleDto;
import com.github.wephotos.webwork.core.service.RoleService;
import com.github.wephotos.webwork.core.utils.ValidationUtil;
import com.github.wephotos.webwork.error.Errors;
import com.github.wephotos.webwork.http.RestObject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 角色
 *
 * @author chengzi
 * @date 2021-01-25 16:55
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Resource
    private RoleService roleService;

    /**
     * 新增角色
     *
     * @param role role
     * @return RestObject
     */
    @PostMapping("/save")
    public RestObject save(@RequestBody RoleDto role) {
        ValidationUtil.isTrue(Errors.ROLE_NAME_EXIST, roleService.checkExistsName(role));
        ValidationUtil.isTrue(Errors.ROLE_CODE_EXIST, roleService.checkExistsCode(role));
        boolean result = roleService.save(role);
        return RestObject.builder().data(result).build();
    }

    /**
     * 修改角色
     *
     * @param role role
     * @return RestObject
     */
    @PostMapping("/update")
    public RestObject update(@RequestBody RoleDto role) {
        ValidationUtil.isTrue(Errors.ROLE_NAME_EXIST, roleService.checkExistsName(role));
        ValidationUtil.isTrue(Errors.ROLE_CODE_EXIST, roleService.checkExistsCode(role));
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
     * 校验角色名称是否唯一
     *
     * @param roleName 角色名称
     * @return RestObject
     */
    @GetMapping("/check-exists-name")
    public RestObject checkExistsName(@RequestParam String roleName) {
        Role role = new Role();
        role.setName(roleName);
        boolean bool = roleService.checkExistsName(role);
        return RestObject.builder().data(bool).build();
    }

    /**
     * 校验角色code是否唯一
     *
     * @param code 角色code
     * @return RestObject
     */
    @GetMapping("/check-exists-code")
    public RestObject checkExistsCode(@RequestParam String code) {
        Role role = new Role();
        role.setCode(code);
        boolean bool = roleService.checkExistsCode(role);
        return RestObject.builder().data(bool).build();
    }
}
