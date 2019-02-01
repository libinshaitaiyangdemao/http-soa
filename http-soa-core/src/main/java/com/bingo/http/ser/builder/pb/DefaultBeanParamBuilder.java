package com.bingo.http.ser.builder.pb;

import com.bingo.http.annotation.Type;
import com.bingo.http.ser.RequestParameter;
import com.bingo.http.ser.RequestParameters;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: libin29
 * @createdate: 2018/12/29 10:37
 * @lastdate:
 */
public class DefaultBeanParamBuilder extends CustomParamBuilder {
    public static class FieldVariable{
        private Field field;

        public FieldVariable(Field field) {
            this.field = field;
            init();
        }
        protected void init(){
            if(!field.isAccessible()){
                field.setAccessible(true);
            }
        }

        public RequestParameter buildParam(Object object){
            try {
                RequestParameter param = new RequestParameter(Type.NORMAL,field.getName(),String.valueOf(field.get(object)));
                return param;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    private List<FieldVariable> fieldVariables;
    public DefaultBeanParamBuilder(Parameter parameter) {
        super(parameter);
    }

    @Override
    protected void init(Parameter parameter) {
        Class<?> clazs = parameter.getType();
        Field[] fields = clazs.getDeclaredFields();
        if(fields != null && fields.length > 0){
            fieldVariables = Arrays.stream(fields).map(FieldVariable::new).collect(Collectors.toList());
        }
    }

    public static boolean support(Parameter parameter) {
        Class<?> clazs = parameter.getType();
        return !isBaseParam(parameter)&&!clazs.isPrimitive();
    }

    private static boolean isBaseParam(Parameter parameter) {
        Class<?> type = parameter.getType();
        if (type.equals(Integer.class) || type.equals(Float.class) || type.equals(Long.class) || type.equals(Double.class) || type.equals(Boolean.class) || type.equals(String.class)) {
            return true;
        }
        return false;
    }
    @Override
    protected String getValue(Object arg) {
        return null;
    }

    @Override
    public void build(RequestParameters params, Object arg) {

        if(arg != null && fieldVariables != null){
            fieldVariables.forEach(fv->params.addParam(fv.buildParam(arg)));
        }
    }
}
