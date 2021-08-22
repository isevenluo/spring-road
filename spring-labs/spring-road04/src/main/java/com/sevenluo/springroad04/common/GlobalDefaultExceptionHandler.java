package com.sevenluo.springroad04.common;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@ResponseBody
class GlobalDefaultExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public Map argumentErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        Map map = new HashMap();
        map.put("error","参数不合法");
        map.put("param",req.getParameterMap());
        return map;
}}