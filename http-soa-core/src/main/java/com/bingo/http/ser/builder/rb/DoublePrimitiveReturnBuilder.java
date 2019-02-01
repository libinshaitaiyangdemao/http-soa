package com.bingo.http.ser.builder.rb;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/2 11:18
 * @lastdate:
 */
public class DoublePrimitiveReturnBuilder extends StringReturnBuilder {

    @Override
    public Object build(byte[] response) {
        String res = (String) super.build(response);
        return Double.parseDouble(res);
    }
}
