package com.bingo.http.ser.builder.pb;

import java.lang.reflect.Parameter;
import java.util.Collection;

/**
 * @description:
 * @author: libin29
 * @createdate: 2018/12/29 10:36
 * @lastdate:
 */
public class CollectionParamBuilder extends CustomParamBuilder {
    public CollectionParamBuilder(Parameter parameter) {
        super(parameter);
    }

    public static boolean support(Parameter parameter) {
        Class<?> clazs = parameter.getType();
        return Collection.class.isAssignableFrom(clazs);
    }

    @Override
    protected String getValue(Object arg) {
        if(arg == null){
            return null;
        }
        Collection c = (Collection) arg;
        StringBuilder builder = new StringBuilder();
        int i = 0;
        for (Object v:c){
            if(i>0){
                builder.append(",");
            }
            builder.append(v);
            i++;
        }
        return builder.toString();
    }
}
