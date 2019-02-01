package com.bingo.http;

import com.bingo.http.ser.MethodInvocationHandler;
import com.bingo.http.ser.builder.pb.ParamBuilderFactory;
import com.bingo.http.ser.builder.rb.ReturnBuilderFactory;
import com.bingo.http.ser.executor.HttpExecutor;
import com.bingo.http.ser.executor.HttpExecutorFactor;
import com.bingo.http.ser.filter.Filter;
import com.bingo.http.utils.ConcurrentLocalStore;

import java.lang.reflect.Proxy;

/**
 * @description:
 * @author: libin29
 * @createdate: 2018/12/24 11:41
 * @lastdate:
 */
public class HttpFactory {
    private static final ConcurrentLocalStore<ServiceSubject<?>,?> STORE = new ConcurrentLocalStore<>((subj)->newInstance(subj.getClazs(),subj.isPreInit(),new HttpExecutorFactor(),new ParamBuilderFactory(),new ReturnBuilderFactory(),subj.getFilters()));

    public static <T> T newInstance(Class<T> clazs,boolean preInit,HttpExecutorFactor hef, ParamBuilderFactory pbf, ReturnBuilderFactory rbf,Filter... filters){
        Class[] clazss = new Class[]{clazs};
        MethodInvocationHandler mih = new MethodInvocationHandler(hef,pbf,rbf,filters);
        if(preInit){
            mih.preInit(clazs);
        }
        return (T) Proxy.newProxyInstance(HttpFactory.class.getClassLoader(),clazss,mih);
    }
    public static <T> T create(Class<T> clazs, Filter...filters){
        ServiceSubject<T> subj = new ServiceSubject(clazs,filters);
        return  (T) STORE.get(subj);
    }

    public static <T> T createPreInit(Class<T> clazs, Filter...filters){
        ServiceSubject<T> subj = new ServiceSubject(clazs,filters,true);
        return  (T) STORE.get(subj);
    }

}

class ServiceSubject<T>{
    private Class<T> clazs;
    private Filter[] filters;
    private boolean preInit;

    public ServiceSubject(Class<T> clazs, Filter... filters) {
        this(clazs,filters,false);
    }

    public ServiceSubject(Class<T> clazs, Filter[] filters, boolean preInit) {
        this.clazs = clazs;
        this.filters = filters;
        this.preInit = preInit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServiceSubject<?> that = (ServiceSubject<?>) o;

        return clazs.equals(that.clazs);
    }

    @Override
    public int hashCode() {
        return clazs.hashCode();
    }

    public Class<T> getClazs() {
        return clazs;
    }

    public Filter[] getFilters() {
        return filters;
    }

    public boolean isPreInit() {
        return preInit;
    }
}
