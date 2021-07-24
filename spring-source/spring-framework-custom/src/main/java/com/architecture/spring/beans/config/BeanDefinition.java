package com.architecture.spring.beans.config;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @outhor: chong
 * @create: 2019-07-16 22:28
 */
public class BeanDefinition {

    private String beanName;
    private String beanClassName;
    private String initMethod;


    /**
     * bean中的属性信息
     */
    private List<PropertyValue> propertyValues = new ArrayList<>();

    public BeanDefinition(String beanClassName, String beanName) {
        this.beanClassName = beanClassName;
        this.beanName = beanName;
    }

    public void setInitMethod(String initMethod) {
        this.initMethod = initMethod;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanClassName() {
        return beanClassName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }

    public String getInitMethod() {
        return initMethod;
    }

    public List<PropertyValue> getPropertyValues() {
        return propertyValues;
    }

    public void addPropertyValue(PropertyValue propertyValue) {
        this.propertyValues.add(propertyValue);
    }


}
