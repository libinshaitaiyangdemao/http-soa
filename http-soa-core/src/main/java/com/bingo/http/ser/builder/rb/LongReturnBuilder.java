package com.bingo.http.ser.builder.rb;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/2 11:16
 * @lastdate:
 */
public class LongReturnBuilder extends StringReturnBuilder {

    @Override
    public Object build(byte[] response) {
        String res = (String) super.build(response);
        return Long.valueOf(res);
    }
}
