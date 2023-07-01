package com.example.springcloudcontract.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.contract.stubrunner.StubFinder;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerConfiguration;
import org.springframework.cloud.contract.stubrunner.spring.cloud.StubMapperProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import retrofit2.Retrofit;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Configuration(proxyBeanMethods = false)
@Import({StubRunnerConfiguration.class, StubResolverConfiguration.class})
@ConditionalOnClass({Retrofit.class, StubFinder.class})
@EnableConfigurationProperties(StubMapperProperties.class)
public class RetrofitStubConfiguration {

    private final StubFinder stubFinder;

    private final StubMapperProperties stubMapperProperties;

    public RetrofitStubConfiguration(StubFinder stubFinder, StubMapperProperties stubMapperProperties) {
        this.stubFinder = stubFinder;
        this.stubMapperProperties = stubMapperProperties;
    }

    @Bean
    public ServiceUrlStub serviceUrlService(Function<StubFinder, Map<String, Integer>> stubResolverFunc) {
        return new ServiceUrlStub(stubResolverFunc.apply(stubFinder));

    }

    private Function<StubFinder, Map<String, Integer>> orDefault(Function<StubFinder, Map<String, Integer>> stubResolverFunc) {
        if (Objects.nonNull(stubResolverFunc)) {
            return stubResolverFunc;
        }
        return new StubResolverConfiguration(stubMapperProperties).defaultStubResolver();
    }
}
