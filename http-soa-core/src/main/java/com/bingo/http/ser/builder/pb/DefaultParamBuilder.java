package com.bingo.http.ser.builder.pb;

import java.lang.reflect.Parameter;

/**
 * @description:
 * @author: libin29
 * @createdate: 2018/12/29 10:37
 * @lastdate:
 */
public class DefaultParamBuilder extends CustomParamBuilder {

    public DefaultParamBuilder(Parameter parameter) {
        super(parameter);
    }

    public static boolean support(Parameter parameter) {
        return true;
    }

    @Override
    protected String getValue(Object arg) {
        if(arg == null){
            return "";
        }
        return arg.toString();
    }
}
