package com.github.wephotos.webwork.controller;


import com.github.wephotos.webwork.entity.Resource;
import com.github.wephotos.webwork.service.ResourceService;
import com.github.wephotos.webwork.utils.RestObject;
import org.springframework.web.bind.annotation.*;

/**
 * 资源菜单
 *
 * @author chengzi
 * @date 2021-01-25 16:55
 */
@RestController
@RequestMapping("/resource")
public class ResourceController {
    @javax.annotation.Resource
    private ResourceService resourceService;

    /**
     * 新增资源
     *
     * @param resource resource
     * @return RestObject
     */
    @PostMapping("/create")
    public RestObject create(@RequestBody Resource resource) {
        boolean result = resourceService.create(resource);
        return RestObject.builder().data(result).build();
    }

    /**
     * 修改资源
     *
     * @param resource resource
     * @return RestObject
     */
    @PostMapping("/update")
    public RestObject update(@RequestBody Resource resource) {
        boolean result = resourceService.update(resource);
        return RestObject.builder().data(result).build();
    }


    /**
     * 根据id获取资源
     *
     * @param id 资源id
     * @return RestObject
     */
    @GetMapping("/get/{id}")
    public RestObject get(@PathVariable String id) {
        Resource resource = resourceService.get(id);
        return RestObject.builder().data(resource).build();
    }

    @GetMapping("/checkResourceNameUnique")
    public RestObject checkResourceNameUnique(@RequestParam String name) {
        Resource resource = new Resource();
        resource.setName(name);
        boolean bool = resourceService.checkResourceNameUnique(resource);
        return RestObject.builder().data(bool).build();
    }

    @GetMapping("/checkResourceCodeUnique")
    public RestObject checkResourceCodeUnique(@RequestParam String code) {
        Resource resource = new Resource();
        resource.setCode(code);
        boolean bool = resourceService.checkResourceCodeUnique(resource);
        return RestObject.builder().data(bool).build();
    }
}
