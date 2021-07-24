package com.sevenluo.custom.spring.framework.annotation;

import java.lang.annotation.*;
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomRequestParam {    String value() default "";}