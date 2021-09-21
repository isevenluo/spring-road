package com.sevenluo.springroad02.config;

import org.springframework.boot.actuate.autoconfigure.trace.http.HttpTraceAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.trace.http.HttpTraceProperties;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Configuration
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "management.trace.http", name = "enabled", matchIfMissing = true)
@EnableConfigurationProperties(HttpTraceProperties.class)
@AutoConfigureBefore(HttpTraceAutoConfiguration.class)
class TraceConfig {
    private List<HttpTrace> list = new CopyOnWriteArrayList<HttpTrace>();

    @Bean
    @ConditionalOnMissingBean(HttpTraceRepository.class)
    public HttpTraceRepository traceRepository() {
        return new HttpTraceRepository() {
            @Override
            public List<HttpTrace> findAll() {
                return list;
            }

            @Override
            public void add(HttpTrace trace) {
                if (list.size() > 99){
                    list.remove(0);
                }
                list.add(trace);
            }
        };
    }
}

