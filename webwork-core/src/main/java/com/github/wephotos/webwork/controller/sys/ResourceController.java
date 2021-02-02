package com.github.wephotos.webwork.controller.sys;


import com.github.wephotos.webwork.entity.sys.Resource;
import com.github.wephotos.webwork.service.sys.ResourceService;
import com.github.wephotos.webwork.util.RestObject;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
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
     * 删除资源
     *
     * @param id 资源id
     * @return RestObject
     */
//    @PostMapping("/delete/{id}")
//    public RestObject delete(@PathVariable String id) {
//        boolean result = resourceService.delete(id);
//        return RestObject.builder().data(result).build();
//    }


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
