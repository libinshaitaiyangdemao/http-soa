package com.bingo.http.ser;

import com.bingo.http.annotation.Type;

/**
 * @description:
 * @author: libin29
 * @createdate: 2018/12/28 17:57
 * @lastdate:
 */
public class UriHolder {
    public static class DefaultUriHolder extends UriHolder {
        private String value;
        public DefaultUriHolder(String name) {
            super(name);
            this.value = name;
        }

        @Override
        public String getValue(RequestParameters params) {
            return value;
        }
    }
    private String name;

    public UriHolder(String name) {
        this.name = name;
    }

    public String getValue(RequestParameters params){
        RequestParameter param = params.getParam(Type.PATHVAL,name);
        if(param == null || param.getValue() == null){
            return "";
        }
        return param.getValue();
    }
}