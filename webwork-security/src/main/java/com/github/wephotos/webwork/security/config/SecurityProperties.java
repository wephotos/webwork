package com.github.wephotos.webwork.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 配置文件
 * @author TQ
 *
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "webwork.security")
public class SecurityProperties {
	
    /**
     * 登录页面
     */
    private String loginUrl = "/";
    
    /**
     * 拦截规则
     */
    private String urlPatterns = "/*";
    /**
     * 忽略路径
     */
    private String ignorePaths = "";
    
}
