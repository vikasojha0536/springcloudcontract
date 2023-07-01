package com.example.springcloudcontract.config;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;
import java.util.Set;

public class RetrofitClientBeanRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private Environment environment;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Set<BeanDefinition> candidateComponents = new RetrofitClientProvider(registry).findCandidateComponents("com.example");
        candidateComponents.forEach(c -> register(c, ((AnnotatedBeanDefinition)c).getMetadata(), registry));

    }

    private void register(BeanDefinition component, AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        String beanClassName = component.getBeanClassName();
        AbstractBeanDefinition abstractBeanDefinition = beanDefinition(metadata, beanClassName);
        BeanDefinitionHolder beanDefinitionHolder =
                new BeanDefinitionHolder(abstractBeanDefinition, beanClassName, new String[]{alias(beanClassName)});
        BeanDefinitionReaderUtils.registerBeanDefinition(beanDefinitionHolder, registry);

    }

    private AbstractBeanDefinition beanDefinition(AnnotationMetadata metadata, String beanClassName) {
        Map<String, Object> annotationAttributes = getAnnotationAttributes(metadata);
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(RetrofitFactoryBean.class);
        beanDefinitionBuilder.addConstructorArgValue(beanClassName);
        String value = resolveProperty(annotationAttributes.get("value").toString());
        String property = resolveProperty(annotationAttributes.get("url").toString());
        beanDefinitionBuilder.addConstructorArgValue(value);
        beanDefinitionBuilder.addConstructorArgValue(property);
        beanDefinitionBuilder.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        return beanDefinitionBuilder.getBeanDefinition();

    }

    private Map<String, Object> getAnnotationAttributes(AnnotationMetadata metadata) {
        return metadata.getAnnotationAttributes(RetrofitClient.class.getName());
    }


    private String resolveProperty(String propertyValue)  {
        return environment.resolvePlaceholders(propertyValue);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    private String alias(String beanName) {
        String className = beanName.substring(beanName.lastIndexOf("."));
        String suffix = RetrofitClient.class.getSimpleName();
        return className + suffix;
    }
}
