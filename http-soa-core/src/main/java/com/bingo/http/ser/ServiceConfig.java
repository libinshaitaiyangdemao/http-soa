package com.bingo.http.ser;

import com.bingo.http.annotation.Type;
import com.bingo.http.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description:
 * @author: libin29
 * @createdate: 2018/12/28 17:24
 * @lastdate:
 */
public class ServiceConfig {
    private static final Pattern URI_PATTEN = Pattern.compile("\\$\\{\\S+?\\}");
    private List<UriHolder> uriHolders;
    private RequestParameters params;

    public ServiceConfig(String host, String uri, RequestParameters params) {
        this.params = params;
        if(host != null && !host.isEmpty()){
            initUriHolders(host + uri);
        }else{
            initUriHolders(uri);
        }
    }
    private void initUriHolders(String uri){
        Matcher matcher = URI_PATTEN.matcher(uri);
        uriHolders = new ArrayList<>();
        int start = 0,end = 0;
        while (matcher.find()){
            start = matcher.start();
            if(start > end){
                uriHolders.add(new UriHolder.DefaultUriHolder(uri.substring(end,start)));
            }
            end = matcher.end();
            String mstr = matcher.group();
            uriHolders.add(new UriHolder(mstr.substring(2,mstr.length()-1)));
        }
        if(end < uri.length()){
            uriHolders.add(new UriHolder.DefaultUriHolder(uri.substring(end)));
        }
    }
    public RequestParameters mergeParams(RequestParameters params){
        params.addParams(this.params);
        return params;
    }

    public String buildUrl(RequestParameters params){
        StringBuilder builder = new StringBuilder();
        uriHolders.forEach(h->builder.append(h.getValue(params)));
        Set<RequestParameter> set = params.getParams(Type.NORMAL);
        if(set != null && !set.isEmpty()){
            builder.append("?");
            int i = 0;
            for(RequestParameter p:set){
                if(i > 0){
                    builder.append("&");
                }
                i ++;
                builder.append(p.getName()).append("=").append(StringUtil.urlEncode(String.valueOf(p.getValue())));
            }
        }
        return builder.toString();
    }
}
