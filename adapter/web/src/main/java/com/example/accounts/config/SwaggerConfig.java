package com.example.accounts.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SwaggerConfig {
    @Bean
    public GroupedOpenApi accountsApi() {
        return GroupedOpenApi.builder()
            .group("accounts")
            .pathsToMatch("/accounts/**")
            .build();
    }
}