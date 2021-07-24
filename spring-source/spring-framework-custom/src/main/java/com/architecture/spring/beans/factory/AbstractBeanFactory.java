package com.architecture.spring.beans.factory;

/**
 * @description:
 * @outhor: chong
 * @create: 2019-07-16 22:26
 */
public abstract class AbstractBeanFactory implements BeanFactory {

    @Override
    public Object getBean(String beanName) {
        return null;
    }

    @Override
    public Object getBean(Class<?> clazz) {
        return null;
    }
}
