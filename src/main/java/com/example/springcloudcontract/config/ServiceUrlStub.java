package com.example.springcloudcontract.config;

import java.util.Map;
import java.util.Objects;

public class ServiceUrlStub {

    private static final String HOST = "http://localhost";

    private final Map<String, Integer> stubsPorts;

    public ServiceUrlStub(Map<String, Integer> stubsPorts) {
        this.stubsPorts = stubsPorts;
    }

    public String stubService(String name) {
        if(Objects.nonNull(stubsPorts.get(name))) {
            return HOST + ":" + stubsPorts.get(name);
    }
        return null;
    }

    boolean hasService(String name) { return stubsPorts.containsKey(name); }
}
