package com.bingo.http.utils;

import java.util.Map;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/8 15:43
 * @lastdate:
 */
public class HttpResponseEntity<T> {

    private int status;
    private T response;
    private Map<String,String> headers;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
}
