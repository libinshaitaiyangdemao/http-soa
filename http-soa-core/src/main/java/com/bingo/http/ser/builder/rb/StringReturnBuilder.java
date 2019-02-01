package com.bingo.http.ser.builder.rb;

import com.bingo.http.ser.executor.HttpResponse;

import java.io.UnsupportedEncodingException;

/**
 * @description:
 *
 * @author: libin29
 * @createdate: 2019/1/2 11:00
 * @lastdate:
 */
public class StringReturnBuilder implements ReturnBuilder {
    @Override
    public Object build(HttpResponse res){
        return build(res.getBytes());
    }

    protected Object build(byte[] bytes){
        try {
            return new String(bytes,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
