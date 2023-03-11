package com.demo.auth.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfiguration {
    final String securitySchemeName = "bearerAuth";

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
            .info(
                new Info()
                    .title("Authentication Service")
                    .description("Spring Security with JWT")
                    .version("1.0.0"))
            .externalDocs(
                new ExternalDocumentation()
                    .description("Blog - janescience.com")
                    .url("https://janescience.com/blog/auth-springboot-jwt"))
            .addSecurityItem(new SecurityRequirement()
                    .addList(securitySchemeName))
            .components(new Components()
                .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                    .name(securitySchemeName)
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")));
    }

    @Bean
    public GroupedOpenApi authApi() {
        return GroupedOpenApi.builder()
            .group("Authentication API")
            .pathsToMatch("/auth/**")
            .build();
    }

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
            .group("User API")
            .pathsToMatch("/user/**")
            .build();
    }
}
