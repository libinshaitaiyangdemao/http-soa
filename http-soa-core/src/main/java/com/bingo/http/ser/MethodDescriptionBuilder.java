package com.bingo.http.ser;

import java.lang.reflect.Method;

/**
 * @description:
 * @author: libin29
 * @createdate: 2018/12/24 15:50
 * @lastdate:
 */
public class MethodDescriptionBuilder {

    /**
     * 构建方法描述符
     *
     * @param method
     * @return
     */
    public static String builder(Method method){
        StringBuilder builder = new StringBuilder();
        builder.append(method.getDeclaringClass()).append(".").append(method.getName());
        builder.append("(");
        Class<?>[] clazs = method.getParameterTypes();
        if(clazs != null && clazs.length > 0){
            int i = 0;
            for(Class<?> c:clazs){
                if(i > 0){
                    builder.append(",");
                }
                builder.append(c);
                i++;
            }
        }
        builder.append(")");
        return builder.toString();
    }
}
