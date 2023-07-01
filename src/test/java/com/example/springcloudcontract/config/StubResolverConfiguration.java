package com.example.springcloudcontract.config;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.contract.stubrunner.StubConfiguration;
import org.springframework.cloud.contract.stubrunner.StubFinder;
import org.springframework.cloud.contract.stubrunner.StubRunner;
import org.springframework.cloud.contract.stubrunner.spring.cloud.StubMapperProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({Retrofit.class, StubRunner.class})
@EnableConfigurationProperties(StubMapperProperties.class)
@AutoConfigureBefore(RetrofitStubConfiguration.class)
public class StubResolverConfiguration {

    private final StubMapperProperties stubMapperProperties;

    public StubResolverConfiguration(StubMapperProperties stubMapperProperties) {
        this.stubMapperProperties = stubMapperProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public Function<StubFinder, Map<String, Integer>> defaultStubResolver() {
        return a -> {
            Map<StubConfiguration, Integer> stubConfigurationIntegerMap = a.findAllRunningStubs().validNamesAndPorts();
            Map<String, Integer> map = new HashMap<>();
            Set<StubConfiguration> stubConfigurations = stubConfigurationIntegerMap.keySet();
            for (StubConfiguration stubConfiguration : stubConfigurations) {
                String artifactId = stubConfiguration.getArtifactId();
                String s = Objects.nonNull(stubName(artifactId)) ? stubName(artifactId) : artifactId;
                map.put(s, stubConfigurationIntegerMap.get(stubConfiguration));

            }
            return map;
        };
    }

    private String stubName(String artifactId) {
        return stubMapperProperties.fromIvyNotationToId(artifactId);
    }
}

