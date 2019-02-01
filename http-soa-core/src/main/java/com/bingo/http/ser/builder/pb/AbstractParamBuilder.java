package com.bingo.http.ser.builder.pb;

import com.bingo.http.annotation.Type;
import com.bingo.http.ser.RequestParameter;
import com.bingo.http.ser.RequestParameters;

/**
 * @description:
 * @author: libin29
 * @createdate: 2018/12/29 10:36
 * @lastdate:
 */
public abstract class AbstractParamBuilder implements ParamBuilder {
    private String name;
    private Type type;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void build(RequestParameters params, Object arg) {
        params.addParam(new RequestParameter(type,name,getValue(arg)));
    }

    protected abstract String getValue(Object arg);
}
