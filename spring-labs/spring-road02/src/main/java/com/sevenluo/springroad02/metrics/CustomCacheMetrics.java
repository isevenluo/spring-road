package com.sevenluo.springroad02.metrics;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * @微信公众号: 七哥聊编程
 * @github: https://github.com/isevenluo
 * @gitee: https://gitee.com/isevenluo
 * @名额不多的个人微信：qige777ya
 * @description: 自定义 Actuator 中的 Metrics端点信息
 * @author: 程序员七哥
 * @create: 2021-09-21 23:24
 **/
@Configuration
public class CustomCacheMetrics implements MeterBinder {
    private static final Logger log = LoggerFactory.getLogger(CustomCacheMetrics.class);

    public CustomCacheMetrics() {
        log.info("custom cache metrics");
    }

    @Override
    public void bindTo(MeterRegistry registry) {
        Gauge.builder("redis.queue.failed", () -> getFailedCount())
                .baseUnit("次数")
                .description("redis queue failed size count")
                .register(registry);
        Gauge.builder("redis.queue.tohandle", () -> getToHandleCount())
                .baseUnit("次数")
                .description("redis queue to handle size count")
                .register(registry);
    }

    private Number getFailedCount() {
        return System.currentTimeMillis()/1000;
    }

    private Number getToHandleCount() {
        return System.currentTimeMillis();
    }
}

