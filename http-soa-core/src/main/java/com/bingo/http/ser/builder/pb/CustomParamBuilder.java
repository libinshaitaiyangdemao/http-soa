package com.bingo.http.ser.builder.pb;

import com.bingo.http.annotation.RequestParam;
import com.bingo.http.annotation.Type;

import java.lang.reflect.Parameter;

/**
 * @description:
 * @author: libin29
 * @createdate: 2018/12/29 10:36
 * @lastdate:
 */
public abstract class CustomParamBuilder extends AbstractParamBuilder {

    public CustomParamBuilder(Parameter parameter){
        init(parameter);
    }
    protected void init(Parameter parameter){
        if(parameter.getAnnotation(RequestParam.class) != null){
            RequestParam rh = parameter.getAnnotation(RequestParam.class);
            setName(rh.name());
            setType(rh.type());
        }else{
            setName(parameter.getName());
            setType(Type.NORMAL);
        }
    }
    public static boolean support(Parameter parameter){
        return false;
    }
}
