package com.bingo.http.ser.filter;

import com.bingo.http.ser.RequestParameters;
import com.bingo.http.ser.executor.HttpResponse;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: libin29
 * @createdate: 2018/12/24 15:33
 * @lastdate:
 */
public class FilterChain {
    private List<Filter> fls;

    public FilterChain(Filter...filters){
        fls = new ArrayList();
        if(filters!=null&&filters.length>0){
            Filter next = filters[filters.length-1];
            fls.add(next);
            for (int i = filters.length - 2; i >=0; i--) {
                Filter f = filters[i];
                f.setNext(next);
                next = f;
                fls.add(0,f);
            }
        }

    }

    public void addFilter(Filter filter){
        if(fls.size() > 0){
            fls.get(fls.size()-1).setNext(filter);
        }
        fls.add(filter);
    }

    public HttpResponse doFilter(Method method, RequestParameters parameters){
        return fls.get(0).doFilter(method,parameters);
    }
}
