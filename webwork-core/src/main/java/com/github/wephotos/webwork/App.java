package com.github.wephotos.webwork;

import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.github.wephotos.webwork.logging.LoggerFactory;

/**
 * @author webwork
 */
@SpringBootApplication
public class App {

	private static final Logger log = LoggerFactory.getLogger(App.class);
	
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        log.debug("===========webwork start success===========");
        try {
        	throw new Exception("异常测试");
        }catch (Exception e) {
			log.error("异常消息测试", e);
		}
    }

}
