package com.bingo.http.ser.filter;

import com.bingo.http.ser.RequestParameters;
import com.bingo.http.ser.executor.HttpResponse;

import java.lang.reflect.Method;

/**
 * @description:
 * @author: libin29
 * @createdate: 2018/12/24 15:38
 * @lastdate:
 */
public class Filter {

    private Filter next;

    void setNext(Filter next) {
        this.next = next;
    }

    public HttpResponse doFilter(Method method, RequestParameters param){
        before(method,param);
        return getNext().doFilter(method,param);
    }

    protected void before(Method method, RequestParameters param){

    }
    protected Filter getNext(){
        return next;
    }

    public boolean support(Method method){
        return true;
    }
}
