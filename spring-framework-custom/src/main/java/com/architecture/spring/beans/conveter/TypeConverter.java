package com.architecture.spring.beans.conveter;

/**
 * @description: 类型转换
 * @outhor: chong
 * @create: 2019-07-16 22:36
 */
public interface TypeConverter {

    boolean isType(Class<?> clazz);

    Object convert(String source);
}
