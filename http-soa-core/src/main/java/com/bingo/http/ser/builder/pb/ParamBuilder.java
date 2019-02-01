package com.bingo.http.ser.builder.pb;

import com.bingo.http.ser.RequestParameters;

/**
 * @description:
 * @author: libin29
 * @createdate: 2018/12/24 16:02
 * @lastdate:
 */
public interface ParamBuilder {

    void build(RequestParameters params, Object arg);
}

