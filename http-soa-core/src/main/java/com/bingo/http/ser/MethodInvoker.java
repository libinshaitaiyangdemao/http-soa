package com.bingo.http.ser;

import com.bingo.http.exception.HttpRequestException;
import com.bingo.http.ser.builder.pb.ParamBuilder;
import com.bingo.http.ser.builder.rb.ReturnBuilder;
import com.bingo.http.ser.executor.HttpExecutor;
import com.bingo.http.ser.executor.HttpResponse;
import com.bingo.http.ser.filter.Filter;
import com.bingo.http.ser.filter.FilterChain;
import com.bingo.http.ser.filter.RequestFilter;

import java.lang.reflect.Method;
import java.util.function.BiFunction;

/**
 * @description:
 * @author: libin29
 * @createdate: 2018/12/24 15:59
 * @lastdate:
 */
public class MethodInvoker {
    private ParamBuilder[] paramBuilders;
    private ReturnBuilder returnBuilder;
    private ServiceConfig config;
    private FilterChain filterChain;
    private HttpExecutor executor;
    private BiFunction<ParamBuilder[], Object[], RequestParameters> parametersFunction;

    public MethodInvoker(ParamBuilder[] pbs, ReturnBuilder rb, FilterChain filterChain, HttpExecutor executor, ServiceConfig config) {
        this.paramBuilders = pbs;
        this.config = config;
        this.returnBuilder = rb;
        this.filterChain = filterChain;
        this.executor = executor;
        if (pbs != null && pbs.length > 0) {
            parametersFunction = (pbuilds, args) -> {
                RequestParameters parameters = new RequestParameters();
                for (int i = 0; i < pbuilds.length; i++) {
                    pbuilds[i].build(parameters, args[i]);
                }
                return parameters;
            };
        } else {
            parametersFunction = (pbuilds, args) -> new RequestParameters();
        }
        this.filterChain.addFilter(new RequestFilter(executor, config));
    }

    public Object invoke(Method method, Object[] args) {
        RequestParameters parameters = parametersFunction.apply(paramBuilders, args);
        config.mergeParams(parameters);
        HttpResponse res = filterChain.doFilter(method, parameters);
        return returnBuilder.build(res);
    }

}
