package com.example.springcloudcontract.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.Collection;
import java.util.Collections;

public class RetrofitFactoryBean implements FactoryBean<Object>, ApplicationContextAware {

    private final Class<?> type;

    private final String name;

    private final String url;

    private ApplicationContext applicationContext;

    public RetrofitFactoryBean(Class<Object> type, String name, String url) {
        this.type = type;
        this.name = name;
        this.url = url;
    }

    @Override
    public Object getObject() {
        return new Retrofit.Builder()
                .baseUrl(new RetrofitUrlFactory().createUrl(name, url, serviceUrlStub()))
                .addConverterFactory(GsonConverterFactory.create())
               // .addCallAdapterFactory()
                .build()
                .create(getObjectType());

    }

//    private Object retrofitClientBuilder() {
//        return applicationContext.getBean(RetrofitClientBuilderFactory.class);
//    }

    @Override
    public Class<?> getObjectType() {
        return type;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private ServiceUrlStub serviceUrlStub() {
        Collection<ServiceUrlStub> values = applicationContext.getBeansOfType(ServiceUrlStub.class).values();
        return values.stream().findFirst().orElse(new ServiceUrlStub(Collections.emptyMap()));
    }
}
