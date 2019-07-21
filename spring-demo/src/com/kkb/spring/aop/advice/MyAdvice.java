package com.kkb.spring.aop.advice;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 编写通知类（增强类）
 * 
 * @author 怡吾宇
 *
 */
public class MyAdvice {

	// 演示前置通知
	public void before() {
		System.out.println("前置通知...");
	}

	// 演示后置通知
	public void afterReturing() {
		System.out.println("后置通知...");
	}

	// 演示最终通知
	public void after() {
		System.out.println("最终通知...");
	}

	// 演示异常抛出通知
	public void afterThrowing() {
		System.out.println("异常抛出通知...");
	}

	/**
	 * 环绕通知 场景使用：事务管理
	 * 
	 * @param joinPoint
	 * @throws Throwable
	 */
	public void aroud(ProceedingJoinPoint joinPoint) {
		System.out.println("环绕通知---前置通知");
		try {
			// 调用目标对象的方法
			joinPoint.proceed();
			System.out.println("环绕通知---后置通知");
		} catch (Throwable e) { // 相当于实现异常通知
			System.out.println("环绕通知---异常抛出配置");
			e.printStackTrace();
		} finally {
			System.out.println("环绕通知---最终通知");
		}
	}

}
