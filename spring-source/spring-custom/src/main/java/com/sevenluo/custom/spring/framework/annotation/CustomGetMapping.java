package com.sevenluo.custom.spring.framework.annotation;

import java.lang.annotation.*;
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomGetMapping {    String value() default "";}