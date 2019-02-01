package com.bingo.http.ser.builder.rb;

import java.lang.reflect.Method;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/2 11:36
 * @lastdate:
 */
public abstract class CustomBeanReturnBuilder implements ReturnBuilder {

    protected Method method;

    public CustomBeanReturnBuilder(Method method) {
        this.method = method;
    }
}
