package com.bingo.http.ser.builder.rb;

import com.bingo.http.ser.executor.HttpResponse;

import java.io.ByteArrayInputStream;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/2 11:29
 * @lastdate:
 */
public class ByteArrayInputStreamReturnBuilder implements ReturnBuilder {

    @Override
    public Object build(HttpResponse response) {
        return new ByteArrayInputStream(response.getBytes());
    }
}
