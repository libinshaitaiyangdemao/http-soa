package com.bingo.http.ser.executor;

import com.bingo.http.annotation.RequestMethod;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/5 9:55
 * @lastdate:
 */
public class HttpExecutorFactor {

    public HttpExecutor create(RequestMethod method){
        return new HttpExecutor(method);
    }
}
