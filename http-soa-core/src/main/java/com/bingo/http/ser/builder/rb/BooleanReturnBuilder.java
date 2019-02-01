package com.bingo.http.ser.builder.rb;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/2 11:19
 * @lastdate:
 */
public class BooleanReturnBuilder extends StringReturnBuilder {

    @Override
    public Object build(byte[] response) {
        String res = (String) super.build(response);
        return Boolean.valueOf(res);
    }
}
