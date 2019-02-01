package com.bingo.http.spring;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import static com.bingo.http.spring.DefaultNamespaceHandler.ATTRIBUTE_EXCLUDES;
import static com.bingo.http.spring.DefaultNamespaceHandler.ATTRIBUTE_FILTERS;
import static com.bingo.http.spring.DefaultNamespaceHandler.ATTRIBUTE_HTTPEXECUTORFACTORY;
import static com.bingo.http.spring.DefaultNamespaceHandler.ATTRIBUTE_PARAMBUILDFACTORY;
import static com.bingo.http.spring.DefaultNamespaceHandler.ATTRIBUTE_PATH;
import static com.bingo.http.spring.DefaultNamespaceHandler.ATTRIBUTE_RETURNBUILDFACTORY;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/4 17:51
 * @lastdate:
 */
public class DefaultNamespaceHandler extends NamespaceHandlerSupport{
    public static final String ATTRIBUTE_PARAMBUILDFACTORY="paramBuilderFactory";
    public static final String ATTRIBUTE_RETURNBUILDFACTORY="returnBuilderFactory";
    public static final String ATTRIBUTE_HTTPEXECUTORFACTORY="httpExecutorFactor";
    public static final String ATTRIBUTE_FILTERS="filters";
    public static final String ATTRIBUTE_PATH = "path";
    public static final String ATTRIBUTE_EXCLUDES = "excludes";
    @Override
    public void init() {
        registerBeanDefinitionParser("scanProcessor",new ScanProcessorBeanDefinitionParser());
        registerBeanDefinitionParser("service",new ServiceProcessorBeanDefinitionParser());
    }
}

class BaseBeanDefinitionParser extends AbstractSimpleBeanDefinitionParser{

    @Override
    protected boolean shouldGenerateId() {
        return true;
    }

    protected void setRefrence(Element element,ParserContext parserContext,BeanDefinitionBuilder builder){
        addPropertyReference(ATTRIBUTE_PARAMBUILDFACTORY,element.getAttribute(ATTRIBUTE_PARAMBUILDFACTORY),builder);
        addPropertyReference(ATTRIBUTE_RETURNBUILDFACTORY,element.getAttribute(ATTRIBUTE_RETURNBUILDFACTORY),builder);
        addPropertyReference(ATTRIBUTE_HTTPEXECUTORFACTORY,element.getAttribute(ATTRIBUTE_HTTPEXECUTORFACTORY),builder);
        addPropertyReference(ATTRIBUTE_FILTERS,element.getAttribute(ATTRIBUTE_FILTERS),builder);
    }

    private void addPropertyReference(String propertyName,String beanName,BeanDefinitionBuilder builder){
        if(StringUtils.hasText(beanName)){
            builder.addPropertyReference(propertyName,beanName);
        }
    }
}
class ScanProcessorBeanDefinitionParser extends BaseBeanDefinitionParser{


    @Override
    protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
        String pathAttr = element.getAttribute(ATTRIBUTE_PATH);
        String[] path = StringUtils.tokenizeToStringArray(pathAttr,",");
        builder.addPropertyValue("scanPaths",path);

        String exAttr = element.getAttribute(ATTRIBUTE_EXCLUDES);
        String[] excludes = StringUtils.tokenizeToStringArray(exAttr,",");
        builder.addPropertyValue(ATTRIBUTE_EXCLUDES,excludes);

        setRefrence(element,parserContext,builder);
    }


    @Override
    protected Class<?> getBeanClass(Element element) {
        return BingoHttpBeanFactoryPostProcessor.class;
    }
}
class ServiceProcessorBeanDefinitionParser extends BaseBeanDefinitionParser{
    @Override
    protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
        String clazs = element.getAttribute("class");
        if(!StringUtils.hasText(clazs)){
            throw new RuntimeException("service 标签的 class 属性不能空");
        }
        builder.addPropertyValue("clazs",clazs);
        setRefrence(element,parserContext,builder);
    }


    @Override
    protected Class<?> getBeanClass(Element element) {
        return ServiceFactoryBean.class;
    }
}