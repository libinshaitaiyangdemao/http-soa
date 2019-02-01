package com.bingo.http.ser.builder.rb;

import com.bingo.http.ser.executor.HttpResponse;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/2 11:28
 * @lastdate:
 */
public class ByteArrayReturnBuilder implements ReturnBuilder {
    @Override
    public Object build(HttpResponse response) {
        return response.getBytes();
    }
}
