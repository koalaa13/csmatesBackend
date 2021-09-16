package com.csmates.wp.config;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;

/**
 * Some constants for jwt configuration
 */
@Configuration
@ConfigurationProperties(prefix = "application.jwt")
public class JwtConfig {
    public JwtConfig() {
    }

    private Long expirationTime;
    private String secretJwt;
    private String tokenPrefix;

    public Algorithm getAlgorithmForSigning() {
        return Algorithm.HMAC512(secretJwt.getBytes(StandardCharsets.UTF_8));
    }

    public String getSecretJwt() {
        return secretJwt;
    }

    public void setSecretJwt(String secretJwt) {
        this.secretJwt = secretJwt;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }

    public Long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }
}
