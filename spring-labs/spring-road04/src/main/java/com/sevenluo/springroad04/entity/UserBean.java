package com.sevenluo.springroad04.entity;

import com.sevenluo.springroad04.conditional.JdbcTemplateConditional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @微信公众号: 七哥聊编程
 * @github: https://github.com/isevenluo
 * @gitee: https://gitee.com/isevenluo
 * @名额不多的个人微信：qige777ya
 * @description:
 * @author: 程序员七哥
 * @create: 2021-08-21 21:46
 **/
@Configuration
public class UserBean {

    @Bean
    @Conditional(JdbcTemplateConditional.class)
    public User getUser() {
        return new User(1L,"条件化bean");
    }
}
