package com.kkb.spring.ioc.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.kkb.spring.ioc.xml.po.Student;

public class TestIoCXML {

	@Test
	public void testInitApplicationContext() throws Exception {
		// 创建IoC容器，并进行初始化
		ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-ioc.xml");
		System.out.println("===============================");
		// 获取Bean的实例，并验证Bean的实例是否是单例模式的
		Student bean = (Student) context.getBean("student");
		Student bean2 = (Student) context.getBean("student");
		System.out.println(bean);
		System.out.println(bean2);
	}

}
