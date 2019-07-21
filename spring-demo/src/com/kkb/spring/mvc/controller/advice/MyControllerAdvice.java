package com.kkb.spring.mvc.controller.advice;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class MyControllerAdvice {

	//应用到所有@RequestMapping注解方法，在其执行之前把返回值放入ModelMap中
	@ModelAttribute
	public Map<String, Object> ma(){
		Map<String, Object> map = new HashMap<>();
		map.put("name", "tom");
		return map;
	}
	
	//应用到所有【带参数】的@RequestMapping注解方法，在其执行之前初始化数据绑定器
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		System.out.println("...initBinder...");
	}
	
	//应用到所有@RequestMapping注解的方法，在其抛出指定异常时执行
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public String handleException(Exception e) {
		return e.getMessage();
	}
}

