package com.github.wephotos.webwork.file;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author chengzi
 * @date 2021-03-10 22:57
 */
@Controller
public class IndexController {
    @RequestMapping("index")
    public String index() {
        return "ind";
    }
}
