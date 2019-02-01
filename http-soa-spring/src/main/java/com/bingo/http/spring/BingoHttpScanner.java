package com.bingo.http.spring;

import com.bingo.http.annotation.RequestMapping;
import com.bingo.http.ser.builder.pb.ParamBuilderFactory;
import com.bingo.http.ser.builder.rb.ReturnBuilderFactory;
import com.bingo.http.ser.executor.HttpExecutorFactor;
import com.bingo.http.ser.filter.Filter;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.util.Set;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/4 16:54
 * @lastdate:
 */
public class BingoHttpScanner extends ClassPathBeanDefinitionScanner {

    private String[] excludes;
    private ParamBuilderFactory paramBuilderFactory;
    private ReturnBuilderFactory returnBuilderFactory;
    private HttpExecutorFactor httpExecutorFactor;
    private Filter[] filters;
    public void setFilters(Filter[] filters) {
        this.filters = filters;
    }
    public void setParamBuilderFactory(ParamBuilderFactory paramBuilderFactory) {
        this.paramBuilderFactory = paramBuilderFactory;
    }

    public void setReturnBuilderFactory(ReturnBuilderFactory returnBuilderFactory) {
        this.returnBuilderFactory = returnBuilderFactory;
    }

    public void setHttpExecutorFactor(HttpExecutorFactor httpExecutorFactor) {
        this.httpExecutorFactor = httpExecutorFactor;
    }
    public BingoHttpScanner(BeanDefinitionRegistry registry,String...excludes) {
        super(registry);
        this.excludes = excludes;
    }

    @Override
    protected void registerDefaultFilters() {
        super.registerDefaultFilters();
        addIncludeFilter(new AnnotationTypeFilter(RequestMapping.class));
        if(excludes != null && excludes.length > 0){
            for (String ex:excludes){
                try {
                    addExcludeFilter(new AssignableTypeFilter(Class.forName(ex)));
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        AnnotationMetadata metadata = beanDefinition.getMetadata();
        return metadata.isInterface();
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> set = super.doScan(basePackages);
        if(set != null){
            set.forEach(holder->{
                GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();
                definition.getPropertyValues().add("clazs", definition.getBeanClassName());
                definition.getPropertyValues().add("paramBuilderFactory", paramBuilderFactory);
                definition.getPropertyValues().add("returnBuilderFactory", returnBuilderFactory);
                definition.getPropertyValues().add("httpExecutorFactor", httpExecutorFactor);
                definition.getPropertyValues().add("filters", filters);
                definition.setBeanClass(ServiceFactoryBean.class);
            });
        }
        return set;
    }
}
