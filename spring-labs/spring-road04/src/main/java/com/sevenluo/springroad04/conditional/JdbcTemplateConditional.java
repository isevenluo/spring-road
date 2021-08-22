package com.sevenluo.springroad04.conditional;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @微信公众号: 七哥聊编程
 * @github: https://github.com/isevenluo
 * @gitee: https://gitee.com/isevenluo
 * @名额不多的个人微信：qige777ya
 * @description: 自定义条件类，实现 Conditional 接口，覆盖 matches 方法
 * 只有在 classpath 里存不在 jdbcTemplate 时才会生效
 * @author: 程序员七哥
 * @create: 2021-08-21 21:39
 **/
public class JdbcTemplateConditional implements Condition {


    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {

        try {
            context.getClassLoader().loadClass("org.springframework.jdbc.core.JdbcTemplate");
            return false;

        } catch (ClassNotFoundException e) {
            return true;
        }

    }
}
