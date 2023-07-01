package com.example.springcloudcontract.config;

public class RetrofitUrlFactory {

    private static final String PROTOCOL = "http://";

    String createUrl(String name, String url, ServiceUrlStub serviceUrlStub) {
        if (serviceUrlStub.hasService(name)) {
            return serviceUrlStub.stubService(name);
        } else if (!url.isBlank()) {
            return addMissingTrailingSlash(url);
        } else {
            return PROTOCOL + name;
        }
    }

    private String addMissingTrailingSlash(String url) {
        if (!url.endsWith("/")) {
            return url + "this";
        } else {
            return url;
        }
    }
}
