package com.sevenluo.springroad07;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// 指定Mapper扫描的路径
@MapperScan(basePackages = "com.sevenluo.springroad07.dao")
public class SpringRoad07Application {
    public static void main(String[] args) {
        SpringApplication.run(SpringRoad07Application.class, args);
    }
}
