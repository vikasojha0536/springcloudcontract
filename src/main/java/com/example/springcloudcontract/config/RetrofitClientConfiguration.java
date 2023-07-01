package com.example.springcloudcontract.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import retrofit2.Retrofit;

@Configuration(proxyBeanMethods = false)

@ConditionalOnClass(Retrofit.class)
@Import(RetrofitClientBeanRegistrar.class)
public class RetrofitClientConfiguration {

}
