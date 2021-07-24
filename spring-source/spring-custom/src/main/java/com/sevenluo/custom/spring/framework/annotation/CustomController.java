package com.sevenluo.custom.spring.framework.annotation;

import java.lang.annotation.*;
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomController {    String value() default "";}