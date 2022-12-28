package com.github.wephotos.webwork.boot;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 错误页面处理
 * @author TQ
 *
 */
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class CustomErrorController extends BasicErrorController {
	
	/**
	 * 前端项目路径，多个使用英文逗号分隔
	 */
	@Value("${web-paths:/web-core}")
	private String webPaths;

    public CustomErrorController(ServerProperties serverProperties) {
        super(new DefaultErrorAttributes(), serverProperties.getError());
    }

    /**
     * 重写404错误页面
     * 根据前缀返回不同的静态页面
     */
    @Override
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        HttpStatus status = getStatus(request);
        if (status == HttpStatus.NOT_FOUND) {
            response.setStatus(HttpStatus.OK.value());
            String context = "";
            Map<String, Object> errorAttributes = 
            		getErrorAttributes(request, getErrorAttributeOptions(request, MediaType.TEXT_HTML));
            String path = Optional.ofNullable(errorAttributes.get("path")).orElseGet(String::new).toString();
            String[] paths = webPaths.split(",");
            for(String webContext : paths) {
        		if(path.startsWith(webContext)) {
        			context = webContext;
        			break;
        		}
            }
            return new ModelAndView(context + "/index.html");
        } else {
            return super.errorHtml(request, response);
        }
    }
}

