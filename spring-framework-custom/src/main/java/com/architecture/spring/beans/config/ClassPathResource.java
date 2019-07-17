package com.architecture.spring.beans.config;

import java.io.InputStream;

public class ClassPathResource implements Resource {

	private String location;

	@Override
	public InputStream getInputStream() {
		if (location == null || "".equals(location)) {
			return null;
		}
		location = location.replace("classpath:", "");
		return this.getClass().getClassLoader().getResourceAsStream(location);
	}

	@Override
	public boolean isCanRead(String location) {
		if (location == null || "".equals(location)) {
			return false;
		}
		if (location.startsWith("classpath:")) {
			this.location = location;
			return true;
		}
		return false;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}