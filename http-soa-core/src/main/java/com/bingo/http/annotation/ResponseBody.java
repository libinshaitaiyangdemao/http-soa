package com.bingo.http.annotation;

import com.bingo.http.ser.builder.rb.CustomBeanReturnBuilder;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description:
 * @author: libin29
 * @createdate: 2018/12/24 11:46
 * @lastdate:
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseBody {

    Class<? extends CustomBeanReturnBuilder> value();
}
