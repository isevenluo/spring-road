package com.sevenluo.springroad06;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching         //开启缓存
@ServletComponentScan("com.sevenluo.springroad06")  // 指定要扫描的Servlet、Filter包路径
public class SpringRoad06Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringRoad06Application.class, args);
    }

}
