package com.github.wephotos.webwork.file.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文件模块HTTP接口
 * @author TQ
 *
 */
@RestController
@RequestMapping("/file")
public class FileController {
	
    @RequestMapping("index")
    public String index() {
        return "ind";
    }
    
    
}
