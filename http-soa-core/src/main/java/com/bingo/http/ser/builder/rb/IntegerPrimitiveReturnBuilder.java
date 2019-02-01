package com.bingo.http.ser.builder.rb;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/2 11:13
 * @lastdate:
 */
public class IntegerPrimitiveReturnBuilder extends StringReturnBuilder {

    @Override
    public Object build(byte[] response) {
        String res = (String) super.build(response);
        return Integer.parseInt(res);
    }
}
