package com.bingo.http.ser.filter;

import com.bingo.http.exception.HttpRequestException;
import com.bingo.http.ser.RequestParameters;
import com.bingo.http.ser.ServiceConfig;
import com.bingo.http.ser.executor.HttpExecutor;
import com.bingo.http.ser.executor.HttpResponse;

import java.lang.reflect.Method;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/7 16:16
 * @lastdate:
 */
public class RequestFilter extends Filter{

    private HttpExecutor execute;
    private ServiceConfig config;

    public RequestFilter(HttpExecutor execute, ServiceConfig config) {
        this.execute = execute;
        this.config = config;
    }

    @Override
    public HttpResponse doFilter(Method method, RequestParameters param) {
        HttpResponse response = execute.execute(config,param);
        if(response.isError()){
            throw new HttpRequestException(response.getStatus(),response.getBody(),response.getHeaders());
        }
        return response;
    }
}
