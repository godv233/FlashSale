package com.java.sale.access;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口限流的注解
 *
 * @author 曾伟
 * @date 2019/11/27 16:14
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AccessLimit {
    int seconds();

    int maxCount();

    boolean needLogin() default true;
}
