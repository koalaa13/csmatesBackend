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
    private String emailConfirmationUrl;

    public String getEmailConfirmationUrl() {
        return emailConfirmationUrl;
    }

    public void setEmailConfirmationUrl(String emailConfirmationUrl) {
        this.emailConfirmationUrl = emailConfirmationUrl;
    }

    public String getSignUpUrl() {
        return signUpUrl;
    }

    public void setSignUpUrl(String signUpUrl) {
        this.signUpUrl = signUpUrl;
    }
}
