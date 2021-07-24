package com.sevenluo.custom.spring.controller;

import com.sevenluo.custom.spring.framework.annotation.CustomAutowired;
import com.sevenluo.custom.spring.framework.annotation.CustomController;
import com.sevenluo.custom.spring.framework.annotation.CustomGetMapping;
import com.sevenluo.custom.spring.framework.annotation.CustomRequestParam;
import com.sevenluo.custom.spring.service.HelloService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@CustomController
public class HelloController {
    @CustomAutowired
    private HelloService helloService;

    @CustomGetMapping("/hello")
    public void query(HttpServletRequest request, HttpServletResponse response, @CustomRequestParam("name") String name) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write("Hello：" + name);
    }
}