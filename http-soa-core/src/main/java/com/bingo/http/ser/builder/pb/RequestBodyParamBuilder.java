package com.bingo.http.ser.builder.pb;

import com.bingo.http.annotation.RequestBody;
import com.bingo.http.annotation.Type;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Parameter;

/**
 * @description:
 * @author: libin29
 * @createdate: 2018/12/29 10:38
 * @lastdate:
 */
public class RequestBodyParamBuilder extends CustomParamBuilder{
    private ObjectMapper mapper = new ObjectMapper();

    public RequestBodyParamBuilder(Parameter parameter) {
        super(parameter);
    }

    @Override
    protected void init(Parameter parameter) {
        setName("RequestBody");
        setType(Type.POST);
    }

    public static boolean support(Parameter parameter) {
        return parameter.getAnnotation(RequestBody.class) != null;
    }

    @Override
    protected String getValue(Object arg) {
        if(arg!=null){
            try {
                return mapper.writeValueAsString(arg);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
