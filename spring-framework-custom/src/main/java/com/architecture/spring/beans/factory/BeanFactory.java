package com.architecture.spring.beans.factory;

public interface BeanFactory {

    /**
     * 根据bean的name获取对象实例
     * @param beanName
     * @return
     */
    Object getBean(String beanName);

    /**
     * 根据bean的类型获取对象实例
     * @param clazz
     * @return
     */
    Object getBean(Class<?> clazz);
}
