package com.sevenluo.springroad06;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching         //开启缓存
public class SpringRoad05Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringRoad05Application.class, args);
    }

}
