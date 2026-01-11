package com.orderkaro.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private final SsoProperties sso;
    public static final String TENANT_HEADER = "X-Tenant-Id";

    public OpenApiConfig(SsoProperties sso) {
        this.sso = sso;
    }

    @Bean
    public OpenAPI openAPI() {

        OAuthFlow authorizationCodeFlow = new OAuthFlow()
                .authorizationUrl(sso.getAuthorizationUrl())
                .tokenUrl(sso.getTokenUrl());

        OAuthFlows flows = new OAuthFlows();
        flows.authorizationCode(authorizationCodeFlow);

        SecurityScheme oauthScheme = new SecurityScheme()
                .type(SecurityScheme.Type.OAUTH2)
                .flows(flows);
        SecurityScheme tenantScheme = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name(TENANT_HEADER)
                .description("Tenant / Organization ID");
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("oauth2", oauthScheme)
                        .addSecuritySchemes("tenant", tenantScheme)
                )
                .addSecurityItem(new SecurityRequirement().addList("oauth2").addList("tenant"));
    }
}
