package com.sevenluo.springroad02.controller;

import com.sevenluo.springroad02.config.MyProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @微信公众号: 七哥聊编程
 * @github: https://github.com/isevenluo
 * @gitee: https://gitee.com/isevenluo
 * @名额不多的个人微信：qige777ya
 * @description:
 * @author: 程序员七哥
 * @create: 2021-09-24 07:43
 **/
@RestController
public class GetValueCOntroller {

    @Resource
    private MyProperties myProperties;

    @GetMapping("/getMaxValue")
    public String getProperties () {
        return "配置的属性值 = " + myProperties.getMaxValue();
    }}
