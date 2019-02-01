package com.bingo.http.ser.executor;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/2 10:19
 * @lastdate:
 */
public class HttpResponse {
    private int status;
    private byte[] bytes;
    private Map<String,String> headers;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
    public boolean isError(){
        return status < 200 || status >= 400;
    }

    public String getBody(){
        try {
            return new String(bytes,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
