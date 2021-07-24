package com.sevenluo.custom.spring.framework.handler;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;
import java.util.Map;

@Getter
@Setter
public class HandlerMapping {
    private String requestUrl;
    //保存方法对应的实例
    private Object target;
    //保存映射的方法
    private Method method;
    //记录方法参数
    private Map<Integer,String> methodParams;
}