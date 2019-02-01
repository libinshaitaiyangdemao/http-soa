package com.bingo.http.ser;

import com.bingo.http.annotation.RequestHeader;
import com.bingo.http.annotation.RequestMapping;
import com.bingo.http.annotation.Type;
import com.bingo.http.ser.builder.pb.ParamBuilder;
import com.bingo.http.ser.builder.pb.ParamBuilderFactory;
import com.bingo.http.ser.builder.rb.ReturnBuilder;
import com.bingo.http.ser.builder.rb.ReturnBuilderFactory;
import com.bingo.http.ser.executor.HttpExecutor;
import com.bingo.http.ser.executor.HttpExecutorFactor;
import com.bingo.http.ser.filter.Filter;
import com.bingo.http.ser.filter.FilterChain;
import com.bingo.http.utils.ConcurrentLocalStore;
import com.bingo.http.utils.StringUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: libin29
 * @createdate: 2018/12/24 16:54
 * @lastdate:
 */
public class MethodInvocationHandler implements InvocationHandler, ConcurrentLocalStore.Loader<Method, MethodInvoker> {

    private ConcurrentLocalStore<Method, MethodInvoker> store;
    private Filter[] filters;

    private ParamBuilderFactory paramBuilderFactory;
    private ReturnBuilderFactory returnBuilderFactory;
    private HttpExecutorFactor httpExecutorFactor;

    public MethodInvocationHandler(Filter... filters){
        this(new HttpExecutorFactor(),new ParamBuilderFactory(),new ReturnBuilderFactory(),filters);
    }
    public MethodInvocationHandler(HttpExecutorFactor httpExecutorFactor,ParamBuilderFactory paramBuilderFactory,ReturnBuilderFactory returnBuilderFactory,Filter... filters) {
        store = new ConcurrentLocalStore<>(this);
        this.filters = filters;
        this.httpExecutorFactor = httpExecutorFactor;
        this.paramBuilderFactory = paramBuilderFactory;
        this.returnBuilderFactory = returnBuilderFactory;
    }

    /**
     * 预加载
     *
     * @param clazs
     */
    public void preInit(Class<?> clazs){
        if(!clazs.isInterface()){
            throw new RuntimeException("预加载对象必须是接口");
        }
        Method[] methods = clazs.getDeclaredMethods();
        if(methods != null && methods.length > 0){
            for (Method m: methods){
                store.get(m);
            }
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MethodInvoker mi = store.get(method);
        if (mi == null) {
            throw new RuntimeException("未找到对应方法：" + MethodDescriptionBuilder.builder(method));
        }
        return mi.invoke(method, args);
    }

    @Override
    public MethodInvoker load(Method method) {
        RequestMapping rm = method.getDeclaringClass().getAnnotation(RequestMapping.class);
        if (rm == null) {
            throw new RuntimeException("方法所属类未找到request mapping");
        }
        String host = StringUtil.removeEnd(rm.host(),"/");
        StringBuilder builder = new StringBuilder();
        builder.append("/");
        builder.append(StringUtil.removeStart(StringUtil.removeEnd(rm.value(), "/"), "/"));
        rm = method.getAnnotation(RequestMapping.class);
        if (rm == null) {
            throw new RuntimeException("方法未找到request mapping");
        }
        String uri = StringUtil.removeStart(StringUtil.removeEnd(rm.value(), "/"), "/");
        if(uri != null && !uri.isEmpty()){
            builder.append("/");
            builder.append(uri);
        }
        RequestParameters rps = new RequestParameters();
        ServiceConfig config = new ServiceConfig(host,builder.toString(),rps);
        RequestHeader rh = method.getAnnotation(RequestHeader.class);
        if (rh != null && rh.value().length > 0) {
            for (String v:rh.value()) {
                String[] hs = v.split(":");
                rps.addParam(new RequestParameter(Type.HEAD,hs[0],hs[1]));
            }
        }
        return new MethodInvoker(createParamBuilders(method), createReturnBuilder(method), createFilterChain(method), httpExecutorFactor.create(rm.method()), config);
    }

    private FilterChain createFilterChain(Method method){
        if(filters == null || filters.length == 0){
            return new FilterChain();
        }
        List<Filter> list = Arrays.stream(filters).filter(filter -> filter.support(method)).collect(Collectors.toList());
        if(list == null || list.isEmpty()){
            return new FilterChain();
        }
        return new FilterChain(list.toArray(new Filter[list.size()]));
    }

    private ReturnBuilder createReturnBuilder(Method method) {
        return returnBuilderFactory.getReturnBuilder(method);
    }

    private ParamBuilder[] createParamBuilders(Method method) {
        Parameter[] pms = method.getParameters();
        ParamBuilder[] pbs = new ParamBuilder[pms.length];
        for (int i = 0; i < pms.length; i++) {
            pbs[i] = paramBuilderFactory.createParamBuilder(pms[i]);
        }
        return pbs;
    }
}