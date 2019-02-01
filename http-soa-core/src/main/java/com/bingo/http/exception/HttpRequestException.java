package com.bingo.http.exception;

import java.util.Map;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/2 11:52
 * @lastdate:
 */
public class HttpRequestException extends RuntimeException {
    private int code;
    private Map<String,String> headers;
    public HttpRequestException(int code, String message,Map<String,String> headers) {
        super(message);
        this.code = code;
        this.headers = headers;
    }

    public int getCode() {
        return code;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
