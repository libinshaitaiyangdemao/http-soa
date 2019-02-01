package com.bingo.http.ser.builder.pb;

import com.bingo.http.annotation.Type;
import com.bingo.http.ser.RequestParameter;
import com.bingo.http.ser.RequestParameters;

import java.lang.reflect.Parameter;
import java.util.Map;

/**
 * @description:
 * @author: libin29
 * @createdate: 2018/12/29 10:37
 * @lastdate:
 */
public class MapParamBuilder extends CustomParamBuilder{
    public MapParamBuilder(Parameter parameter) {
        super(parameter);
    }

    public static boolean support(Parameter parameter) {
        Class<?> clazs = parameter.getType();
        return Map.class.isAssignableFrom(clazs);
    }

    @Override
    protected String getValue(Object arg) {
        return null;
    }

    @Override
    public void build(RequestParameters params, Object arg) {
        if(arg == null){
            return;
        }
        Map m = (Map) arg;
        m.forEach((k,v)->params.addParam(new RequestParameter(Type.NORMAL,String.valueOf(k),String.valueOf(v))));
    }
}
