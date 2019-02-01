package com.bingo.http.ser.executor;

import com.bingo.http.annotation.RequestMethod;
import com.bingo.http.annotation.Type;
import com.bingo.http.ser.RequestParameter;
import com.bingo.http.ser.RequestParameters;
import com.bingo.http.ser.ServiceConfig;
import com.bingo.http.utils.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description:
 * @author: libin29
 * @createdate: 2018/12/28 18:38
 * @lastdate:
 */
public class HttpExecutor {

    private RequestMethod method;

    public HttpExecutor(RequestMethod method) {
        this.method = method;
    }

    public HttpResponse execute(ServiceConfig config, RequestParameters params){
        try {
            HttpURLConnection connection = createHttpClient(config.buildUrl(params),params);
            connection.connect();
            if(method.isOutput()){
                doOutput(connection,params);
            }
            HttpResponse response = new HttpResponse();
            response.setStatus(connection.getResponseCode());
            setResponseHeaders(response,connection.getHeaderFields());
            if(response.isError()){
                setBody(response,connection.getErrorStream());
            }else{
                setBody(response,connection.getInputStream());
            }
            connection.disconnect();
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void doOutput(HttpURLConnection connection,RequestParameters params) throws IOException{
        RequestParameter param = params.getParam(Type.POST,"RequestBody");
        if(param != null && param.getValue() != null){
            OutputStream out = connection.getOutputStream();
            Utils.writeString(out, param.getValue());
            out.close();
        }
    }
    private void setBody(HttpResponse response, InputStream in) throws IOException {
        response.setBytes(Utils.readByte(in));
    }
    private void setResponseHeaders(HttpResponse response, Map<String,List<String>> headFields){
        if(headFields == null || headFields.isEmpty()){
            return;
        }
        Map<String,String> map = new HashMap<>(headFields.size());
        headFields.forEach((k,v)->{
            if(v != null && !v.isEmpty()){
                if(v.size() == 1){
                    map.put(k,v.get(0));
                }else{
                    map.put(k, v.stream().reduce("",String::concat));
                }
            }
        });
        response.setHeaders(map);
    }
    protected HttpURLConnection createHttpClient(String urlStr,RequestParameters parameters) throws Exception{
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method.toString());
        connection.setDoInput(true);
        connection.setDoOutput(method.isOutput());
        Set<RequestParameter> set = parameters.getParams(Type.HEAD);
        if(set != null && !set.isEmpty()){
            set.forEach(p->connection.addRequestProperty(p.getName(),p.getValue()));
        }
        set = parameters.getParams(Type.COOKIE);
        if(set != null && !set.isEmpty()){
            set.forEach(p->{
                if(p.getName() == null || p.getName().isEmpty()){
                    connection.addRequestProperty("Cookie",p.getValue());
                }else{
                    connection.addRequestProperty("Cookie",p.getName()+"="+p.getValue());
                }
            });
        }
        return connection;
    }
}
