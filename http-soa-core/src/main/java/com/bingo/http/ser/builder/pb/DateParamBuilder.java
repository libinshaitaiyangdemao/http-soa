package com.bingo.http.ser.builder.pb;

import com.bingo.http.annotation.RequestParam;

import java.lang.reflect.Parameter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description:
 * @author: libin29
 * @createdate: 2018/12/29 10:37
 * @lastdate:
 */
public class DateParamBuilder extends CustomParamBuilder {
    private DateFormat df;

    public DateParamBuilder(Parameter parameter) {
        super(parameter);
    }

    @Override
    protected void init(Parameter parameter) {
        super.init(parameter);
        RequestParam rp = parameter.getAnnotation(RequestParam.class);
        if(rp != null && rp.value() != null && !rp.value().isEmpty()){
            df = new SimpleDateFormat(rp.value());
        }
    }

    public static boolean support(Parameter parameter) {
        Class<?> clazs = parameter.getType();
        return Date.class.isAssignableFrom(clazs);
    }


    @Override
    protected String getValue(Object arg) {
        if(df == null){
            return String.valueOf(((Date)arg).getTime());
        }
        return df.format(arg);
    }
}
