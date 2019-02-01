package com.bingo.http.ser.builder.pb;

import java.lang.reflect.Array;
import java.lang.reflect.Parameter;

/**
 * @description:
 * @author: libin29
 * @createdate: 2018/12/29 10:36
 * @lastdate:
 */
public class ArrayParamBuilder extends CustomParamBuilder {

    public ArrayParamBuilder(Parameter parameter) {
        super(parameter);
    }

    public static boolean support(Parameter parameter) {
        Class<?> clazs = parameter.getType();
        return clazs.isArray();
    }

    @Override
    protected String getValue(Object arg) {
        if(arg == null){
            return null;
        }
        int l = Array.getLength(arg);
        StringBuilder builder = new StringBuilder();
        for (int i = 0;i<l;i++){
            if(i>0){
                builder.append(",");
            }
            builder.append(Array.get(arg,i));
        }
        return builder.toString();
    }
}
