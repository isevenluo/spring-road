package com.kkb.spring.aop.target;

import org.springframework.stereotype.Service;

/**
 * AOP中的目标对象
 * 
 * @author 怡吾宇
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Override
	public void saveUser() {
		System.out.println("添加用户");
		// 抛出异常的代码
		// System.out.println(1 / 0);
	}
	@Override
	public void saveUser(String name ) {
		System.out.println("添加用户 : name");
	}

	@Override
	public void updateUser() {
		System.out.println("修改用户");
	}

}
