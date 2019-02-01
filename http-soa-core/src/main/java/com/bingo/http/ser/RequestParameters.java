package com.bingo.http.ser;

import com.bingo.http.annotation.Type;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @description:
 * @author: libin29
 * @createdate: 2018/12/28 17:30
 * @lastdate:
 */
public class RequestParameters {
    private Map<Type,Set<RequestParameter>> map;

    public RequestParameters() {
        this.map = new HashMap<>();
    }

    public RequestParameter getParam(Type type, String name){
        Set<RequestParameter> set = map.get(type);
        if(set == null || set.isEmpty()){
            return null;
        }
        for(RequestParameter p:set){
            if(p.getName().equals(name)){
                return p;
            }
        }
        return null;
    }
    public Set<RequestParameter> getParams(Type type){
        return map.get(type);
    }

    public void addParam(RequestParameter param){
        Set<RequestParameter> set = map.get(param.getType());
        if(set == null){
            set = new HashSet<>();
            map.put(param.getType(),set);
        }
        set.add(param);
    }

    public void addParams(Collection<RequestParameter> params){
        params.forEach(this::addParam);
    }

    private void addParams(Type type, Collection<RequestParameter> params){
        Set<RequestParameter> set = map.get(type);
        if(set == null){
            set = new HashSet<>();
            map.put(type,set);
        }
        set.addAll(params);
    }
    public void addParams(RequestParameters params){
        params.map.forEach(this::addParams);
    }
}
