package com.bingo.http.ser.builder.rb;

import java.math.BigDecimal;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/2 11:20
 * @lastdate:
 */
public class BigDecimalReturnBuilder extends StringReturnBuilder {

    @Override
    public Object build(byte[] response) {
        String res = (String) super.build(response);
        return new BigDecimal(res);
    }
}
