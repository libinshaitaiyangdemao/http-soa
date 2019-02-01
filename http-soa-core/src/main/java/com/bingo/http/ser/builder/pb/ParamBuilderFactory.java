package com.bingo.http.ser.builder.pb;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @description:
 * @author: libin29
 * @createdate: 2018/12/25 9:42
 * @lastdate:
 */
public class ParamBuilderFactory {
    private List<Class<? extends CustomParamBuilder>> customParamBuilderClasses;
    public ParamBuilderFactory() {
        customParamBuilderClasses = Collections.synchronizedList(new ArrayList<>());
        addCustomParamBuilderClass(DefaultBeanParamBuilder.class);
        addCustomParamBuilderClass(ArrayParamBuilder.class);
        addCustomParamBuilderClass(CollectionParamBuilder.class);
        addCustomParamBuilderClass(MapParamBuilder.class);
        addCustomParamBuilderClass(DateParamBuilder.class);
        addCustomParamBuilderClass(RequestBodyParamBuilder.class);
    }

    public void addCustomParamBuilderClass(Class<? extends CustomParamBuilder> custom) {
        customParamBuilderClasses.add(0,custom);
    }

    public ParamBuilder createParamBuilder(Parameter parameter) {
        for(int i = 0;i<customParamBuilderClasses.size();i++){
            Class<? extends CustomParamBuilder> clazs = customParamBuilderClasses.get(i);
            try {
                Method method = clazs.getMethod("support", Parameter.class);
                if(method == null || !Modifier.isStatic(method.getModifiers())){
                    throw  new RuntimeException("自定义属性构造器，必须有名称为support,入参为java.lang.reflect.Parameter的静态方法");
                }
                Boolean support = (Boolean) method.invoke(null,parameter);
                if(!support){
                    continue;
                }
                Constructor<? extends CustomParamBuilder> constructor = clazs.getConstructor(Parameter.class);
                return constructor.newInstance(parameter);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("自定义属性构造器，必须有入参为java.lang.reflect.Parameter的构造方法");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return new DefaultParamBuilder(parameter);
    }
}
