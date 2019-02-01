package com.bingo.http.ser.builder.rb;

import com.bingo.http.ser.executor.HttpResponse;

/**
 * @description:
 * @author: libin29
 * @createdate: 2018/12/24 16:04
 * @lastdate:
 */
public interface ReturnBuilder {
    ReturnBuilder NULL_BUILDER = (res)->null;
    Object build(HttpResponse res);
}