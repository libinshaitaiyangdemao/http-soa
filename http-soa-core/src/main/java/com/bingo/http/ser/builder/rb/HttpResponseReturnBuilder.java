package com.bingo.http.ser.builder.rb;

import com.bingo.http.ser.executor.HttpResponse;
import com.bingo.http.utils.HttpResponseEntity;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/8 14:53
 * @lastdate:
 */
public class HttpResponseReturnBuilder implements ReturnBuilder {

    private ReturnBuilder returnBuilder;

    public HttpResponseReturnBuilder(ReturnBuilder returnBuilder) {
        this.returnBuilder = returnBuilder;
    }

    @Override
    public Object build(HttpResponse res) {
        HttpResponseEntity entity = new HttpResponseEntity();
        entity.setStatus(res.getStatus());
        entity.setHeaders(res.getHeaders());
        entity.setResponse(returnBuilder.build(res));
        return entity;
    }
}
