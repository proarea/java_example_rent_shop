package com.snowboard_rental_crm.gateway.auth.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthWhiteListConfig {

    @Bean
    @Qualifier("authWhiteList")
    public String[] getAuthWhiteList() {
        return new String[]{
                "/v1/auth",
                "/v1/auth/refresh-token",
                "/v1/auth/registrations",
                // -- swagger ui
                "/v3/api-docs",
                "/v3/api-docs/*",
                "/swagger-resources",
                "/documentation/swagger-ui.html",
                "/swagger-resources/**",
                "/swagger-resources/configuration/ui",
                "/swagger-resources/configuration/security",
                "/swagger-ui.html",
                "/swagger-ui/*",
                "/webjars/**",
                // other public endpoints of your API may be appended to this array
        };
    }

}
