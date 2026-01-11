package com.orderkaro.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "sso")
public class SsoProperties {
    private String baseurl;
    private String realm;
    private String authorizationUrl;
    private String tokenUrl;
    private String clientId;
    private String clientSecret;
    private String certs;
    private String issuer;

}

