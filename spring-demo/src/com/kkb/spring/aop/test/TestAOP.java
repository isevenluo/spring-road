package com.kkb.spring.aop.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kkb.spring.aop.target.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/spring-aop.xml")
public class TestAOP {

	@Autowired
	private UserService userService;
	
	@Test
	public void test() {
		userService.saveUser();
		System.out.println("=================");
		userService.saveUser("lisi");
		System.out.println("=================");
		userService.updateUser();
	}
}
