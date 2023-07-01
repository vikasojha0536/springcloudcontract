package com.example.springcloudcontract.config;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

public class RetrofitClientProvider extends ClassPathScanningCandidateComponentProvider {

    private final BeanDefinitionRegistry beanDefinitionRegistry;

    public RetrofitClientProvider(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
        addIncludeFilter(new AnnotationTypeFilter(RetrofitClient.class, true, true));
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        boolean anInterface = beanDefinition.getMetadata().isInterface();
          if(!anInterface) {

          }
          return anInterface;
    }

    @Override
    protected BeanDefinitionRegistry getRegistry() {
        return this.beanDefinitionRegistry;
    }
}
