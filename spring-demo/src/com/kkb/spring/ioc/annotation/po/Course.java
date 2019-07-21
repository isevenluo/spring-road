package com.kkb.spring.ioc.annotation.po;

import org.springframework.stereotype.Component;

@Component
public class Course {

	private String name;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
