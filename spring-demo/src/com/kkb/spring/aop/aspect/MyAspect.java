package com.kkb.spring.aop.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;

@Component("myAspect")
@Aspect
public class MyAspect {
	
	private static final String pcut="execution(* *..*.*ServiceImpl.*(..))";
	
	@Before(value="MyAspect.fn()")
	public void before() {
		System.out.println("这是注解方式的前置通知");
	}
	@AfterReturning(pcut)
	public void after() {
		System.out.println("这是注解方式的后置通知");
	}
	
	@Pointcut("execution(* *..*.*ServiceImpl.*(..))")
	public void fn() {}
}
