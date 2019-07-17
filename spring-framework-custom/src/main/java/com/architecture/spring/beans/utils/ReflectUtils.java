package com.architecture.spring.beans.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectUtils {

	public static Object createObject(String beanClassName, Object... args) {
		try {
			Class<?> clazz = Class.forName(beanClassName);
			Constructor<?> constructor = clazz.getConstructor();
			// 默认调用无参构造进行对象的创建
			return constructor.newInstance(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void setProperty(Object beanInstance, String name, Object valueToUse) {
		try {
			Class<?> clazz = beanInstance.getClass();
			Field field = clazz.getDeclaredField(name);
			field.setAccessible(true);
			field.set(beanInstance, valueToUse);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Class<?> getTypeByFieldName(String beanClassName, String name) {
		try {
			Class<?> clazz = Class.forName(beanClassName);
			Field field = clazz.getDeclaredField(name);
			return field.getType();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void invokeMethod(Object beanInstance, String initMethod) {
		try {
			Class<?> clazz = beanInstance.getClass();
			Method method = clazz.getDeclaredMethod(initMethod);
			method.setAccessible(true);
			method.invoke(beanInstance);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
