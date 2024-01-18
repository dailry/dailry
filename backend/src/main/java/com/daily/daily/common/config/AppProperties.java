package com.daily.daily.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private static String dataStorageDomain;
    private static String frontendDomain;

    public static String getDataStorageDomain() {
        return dataStorageDomain;
    }

    public static String getFrontendDomain() {
        return frontendDomain;
    }
}
