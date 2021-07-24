package com.sevenluo.custom.spring.framework.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyConfig {

    private String basePackages;

    public String getBasePackages() {
        return basePackages;
    }

    public void setBasePackages(String basePackages) {
        this.basePackages = basePackages;
    }
}
