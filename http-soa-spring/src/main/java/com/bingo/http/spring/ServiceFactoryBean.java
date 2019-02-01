package com.bingo.http.spring;

import com.bingo.http.HttpFactory;
import com.bingo.http.ser.builder.pb.ParamBuilderFactory;
import com.bingo.http.ser.builder.rb.ReturnBuilderFactory;
import com.bingo.http.ser.executor.HttpExecutorFactor;
import com.bingo.http.ser.filter.Filter;
import org.springframework.beans.factory.FactoryBean;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/4 16:37
 * @lastdate:
 */
public class ServiceFactoryBean<T> implements FactoryBean<T> {

    private Class<?> clazs;

    private Filter[] filters;

    private ParamBuilderFactory paramBuilderFactory;
    private ReturnBuilderFactory returnBuilderFactory;
    private HttpExecutorFactor httpExecutorFactor;

    public void setParamBuilderFactory(ParamBuilderFactory paramBuilderFactory) {
        this.paramBuilderFactory = paramBuilderFactory;
    }

    public void setReturnBuilderFactory(ReturnBuilderFactory returnBuilderFactory) {
        this.returnBuilderFactory = returnBuilderFactory;
    }

    public void setHttpExecutorFactor(HttpExecutorFactor httpExecutorFactor) {
        this.httpExecutorFactor = httpExecutorFactor;
    }
    public ParamBuilderFactory getParamBuilderFactory() {
        if(paramBuilderFactory == null){
            paramBuilderFactory = new ParamBuilderFactory();
        }
        return paramBuilderFactory;
    }

    public ReturnBuilderFactory getReturnBuilderFactory() {
        if(returnBuilderFactory == null){
            return new ReturnBuilderFactory();
        }
        return returnBuilderFactory;
    }

    public HttpExecutorFactor getHttpExecutorFactor() {
        if(httpExecutorFactor == null){
            httpExecutorFactor = new HttpExecutorFactor();
        }
        return httpExecutorFactor;
    }
    public T getObject() throws Exception {
        return (T)HttpFactory.newInstance(clazs,true,getHttpExecutorFactor(),getParamBuilderFactory(),getReturnBuilderFactory());
    }

    public Class<?> getObjectType() {
        return clazs;
    }

    public boolean isSingleton() {
        return true;
    }

    public void setClazs(String className) {
        try {
            this.clazs = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void setFilters(Filter[] filters) {
        this.filters = filters;
    }
}
