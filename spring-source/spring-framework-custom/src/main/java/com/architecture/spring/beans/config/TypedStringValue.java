package com.architecture.spring.beans.config;

public class TypedStringValue {
	
	private String value;
	
	private Class<?> targetType;

	public TypedStringValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Class<?> getTargetType() {
		return targetType;
	}

	public void setTargetType(Class<?> targetType) {
		this.targetType = targetType;
	}
	
	

}