package com.bingo.http.ser.builder.rb;

import com.bingo.http.annotation.ResponseBody;
import com.bingo.http.ser.executor.HttpResponse;
import com.bingo.http.utils.ConcurrentLocalStore;
import com.bingo.http.utils.HttpResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @description:
 * @author: libin29
 * @createdate: 2018/12/25 9:14
 * @lastdate:
 */
public class ReturnBuilderFactory {
    private ConcurrentMap<Class<?>, ReturnBuilder> store;

    public ReturnBuilderFactory() {
        store = new ConcurrentHashMap<>();
        store.put(Void.TYPE, ReturnBuilder.NULL_BUILDER);
        store.put(Integer.class, new IntegerReturnBuilder());
        store.put(int.class, new IntegerPrimitiveReturnBuilder());
        store.put(long.class, new LongPrimitiveReturnBuilder());
        store.put(Long.class, new LongReturnBuilder());
        store.put(float.class, new FloatPrimitiveReturnBuilder());
        store.put(Float.class, new FloatReturnBuilder());
        store.put(double.class, new DoublePrimitiveReturnBuilder());
        store.put(Double.class, new DoubleReturnBuilder());
        store.put(boolean.class, new BooleanPrimitiveReturnBuilder());
        store.put(Boolean.class, new BooleanReturnBuilder());
        store.put(String.class, new StringReturnBuilder());
        store.put(BigDecimal.class, new BigDecimalReturnBuilder());
        store.put(byte[].class, new ByteArrayReturnBuilder());
        store.put(ByteArrayInputStream.class, new ByteArrayInputStreamReturnBuilder());
        store.put(InputStream.class, store.get(ByteArrayInputStream.class));
    }


    public void setReturnBuilder(Class<?> clazs, ReturnBuilder builder) {
        store.put(clazs, builder);
    }

    public ReturnBuilder getReturnBuilder(Method method) {
        ReturnBuilder rb = createResponseBodyReturnBuilder(method);
        if (rb != null) {
            return rb;
        }
        Class<?> clazs = method.getReturnType();
        rb = store.get(clazs);
        if (rb != null) {
            return rb;
        }
        if (clazs.equals(HttpResponseEntity.class)) {
            return createHttpResponseReturnBuilder(method);
        }
        return createDefaultBeanReturnBuilder(method);
    }

    protected ReturnBuilder createHttpResponseReturnBuilder(Method method) {
        return createHttpResponseReturnBuilder(method.getGenericReturnType());
    }

    protected ReturnBuilder createHttpResponseReturnBuilder(Type type) {
        Class clazs = getRawType(type);
        if (!HttpResponseEntity.class.equals(clazs)) {
            if (clazs.equals(Object.class)) {
                return store.get(byte[].class);
            } else {
                ReturnBuilder rb = store.get(clazs);
                if (rb == null) {
                    rb = new DefaultBeanReturnBuilder(clazs);
                }
                return rb;
            }
        } else {
            ReturnBuilder innerRB = null;
            if (!(type instanceof ParameterizedType)) {
                innerRB = store.get(byte[].class);
            } else {
                ParameterizedType pt = (ParameterizedType) type;
                Type[] types = pt.getActualTypeArguments();
                if (types != null && types.length == 1) {
                    innerRB = createHttpResponseReturnBuilder(types[0]);
                } else {
                    innerRB = store.get(byte[].class);
                }
            }
            return new HttpResponseReturnBuilder(innerRB);
        }
    }

    private Class getRawType(Type type) {
        if(type instanceof Class){
            return (Class) type;
        }else if(type instanceof ParameterizedType){
            ParameterizedType pt = (ParameterizedType) type;
            return (Class) pt.getRawType();
        }
        String cn = type.getTypeName();
        try {
            return Class.forName(cn);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected ReturnBuilder createDefaultBeanReturnBuilder(Method method) {
        return createDefaultBeanReturnBuilder(method.getReturnType());
    }

    protected ReturnBuilder createDefaultBeanReturnBuilder(Class clazs) {
        return new DefaultBeanReturnBuilder(clazs);
    }

    protected ReturnBuilder createResponseBodyReturnBuilder(Method method) {
        ResponseBody responseBody = method.getAnnotation(ResponseBody.class);
        if (responseBody != null) {
            Class<? extends CustomBeanReturnBuilder> rbClass = responseBody.value();
            try {
                Constructor<? extends CustomBeanReturnBuilder> constructor = rbClass.getConstructor(Method.class);
                return constructor.newInstance(method);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
