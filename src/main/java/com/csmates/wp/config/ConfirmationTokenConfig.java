package com.csmates.wp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application.confirmation")
public class ConfirmationTokenConfig {
    public ConfirmationTokenConfig() {
    }
    private Long tokenExpirationTime;

    public Long getTokenExpirationTime() {
        return tokenExpirationTime;
    }

    public void setTokenExpirationTime(Long tokenExpirationTime) {
        this.tokenExpirationTime = tokenExpirationTime;
    }
}
