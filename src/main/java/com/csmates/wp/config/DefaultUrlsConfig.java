package com.csmates.wp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Some useful in code urls
 */
@Configuration
@ConfigurationProperties(prefix = "application.urls")
public class DefaultUrlsConfig {
    public DefaultUrlsConfig() {
    }

    private String signUpUrl;

    public String getSignUpUrl() {
        return signUpUrl;
    }

    public void setSignUpUrl(String signUpUrl) {
        this.signUpUrl = signUpUrl;
    }
}
