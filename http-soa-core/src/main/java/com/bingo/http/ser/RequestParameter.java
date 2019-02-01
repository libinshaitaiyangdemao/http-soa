package com.bingo.http.ser;

import com.bingo.http.annotation.Type;

/**
 * @description:
 * @author: libin29
 * @createdate: 2018/12/28 17:27
 * @lastdate:
 */
public class RequestParameter {
    private Type type;
    private String name;
    private String value;

    public RequestParameter() {
    }

    public RequestParameter(Type type, String name, String value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

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

    public String getValue() {
        if(value == null){
            return "";
        }
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequestParameter that = (RequestParameter) o;

        if (type != that.type) return false;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}
