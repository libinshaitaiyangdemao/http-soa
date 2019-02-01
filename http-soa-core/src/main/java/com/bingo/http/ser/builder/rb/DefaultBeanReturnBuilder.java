package com.bingo.http.ser.builder.rb;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/2 11:33
 * @lastdate:
 */
public class DefaultBeanReturnBuilder extends StringReturnBuilder {

    private ObjectMapper objectMapper = new ObjectMapper();
    private Class<?> clazs;

    public DefaultBeanReturnBuilder(Class<?> clazs) {
        this.clazs = clazs;
    }

    @Override
    public Object build(byte[] response) {
        String json = (String) super.build(response);
        try {
            return objectMapper.readValue(json,clazs);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
