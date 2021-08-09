package com.sevenluo.springroad02.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @微信公众号: 七哥聊编程
 * @github: https://github.com/isevenluo
 * @gitee: https://gitee.com/isevenluo
 * @名额不多的个人微信：qige777ya
 * @description: 专门用来加载配置的属性
 * @author: 程序员七哥
 * @create: 2021-08-08 13:02
 **/
@Component
@ConfigurationProperties(prefix= "my" )
@PropertySource(value = {"classpath:my.properties"})
@Data
public class MyProperties {

    private int maxValue= 0;

}
