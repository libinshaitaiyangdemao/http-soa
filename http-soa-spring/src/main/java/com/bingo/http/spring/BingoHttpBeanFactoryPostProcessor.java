package com.bingo.http.spring;

import com.bingo.http.ser.builder.pb.ParamBuilderFactory;
import com.bingo.http.ser.builder.rb.ReturnBuilderFactory;
import com.bingo.http.ser.executor.HttpExecutorFactor;
import com.bingo.http.ser.filter.Filter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/4 16:53
 * @lastdate:
 */
public class BingoHttpBeanFactoryPostProcessor implements BeanFactoryPostProcessor,ApplicationContextAware {

    private String[] scanPaths;
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

    public void setExcludes(String[] excludes) {
        this.excludes = excludes;
    }

    private ApplicationContext applicationContext;
    public void setScanPaths(String[] scanPaths) {
        this.scanPaths = scanPaths;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        if(scanPaths != null && scanPaths.length > 0){
            BingoHttpScanner scanner = new BingoHttpScanner((BeanDefinitionRegistry) configurableListableBeanFactory,excludes);
            scanner.setHttpExecutorFactor(httpExecutorFactor);
            scanner.setParamBuilderFactory(paramBuilderFactory);
            scanner.setReturnBuilderFactory(returnBuilderFactory);
            scanner.setFilters(filters);
            scanner.setResourceLoader(applicationContext);
            scanner.scan(scanPaths);
        }
    }
}
