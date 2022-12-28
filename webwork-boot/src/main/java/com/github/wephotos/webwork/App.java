package com.github.wephotos.webwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class App {
	
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        log.debug("===========webwork start success===========");
    }

}
