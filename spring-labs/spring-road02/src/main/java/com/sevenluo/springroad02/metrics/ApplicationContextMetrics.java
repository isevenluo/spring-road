package com.sevenluo.springroad02.metrics;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.geo.Metric;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * @微信公众号: 七哥聊编程
 * @github: https://github.com/isevenluo
 * @gitee: https://gitee.com/isevenluo
 * @名额不多的个人微信：qige777ya
 * @description: 自定义Metrics，实现通过metrics端点展示应用上下文启动时间、Bean及Bean定义的数量、
 * 加了@controller注解的bean数量
 * @author: 程序员七哥
 * @create: 2021-09-21 23:42
 **/
@Component
public class ApplicationContextMetrics implements MeterBinder {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void bindTo(MeterRegistry registry) {
        // 常见的Meter有Counter（计数），Gauge（数值），Timer（一段时间内的统计）
        Gauge.builder("spring.context.startup-date",
                () -> applicationContext.getStartupDate())
                .baseUnit("timestamp (ms)").description("应用上下文启动时间").register(registry);
        Gauge.builder("spring.beans.defintions",() -> applicationContext.getBeanDefinitionCount())
                .baseUnit("个").description("bean定义的数量").register(registry);

        Gauge.builder("spring.beans",() -> applicationContext.getBeanNamesForType(Object.class).length)
                .baseUnit("名字").description("spring上下文中定义的所有bean").register(registry);

        Gauge.builder("spring.controllers",() -> applicationContext.getBeanNamesForAnnotation(Controller.class).length)
                .description("控制器类型bean的数量").register(registry);
    }
}
