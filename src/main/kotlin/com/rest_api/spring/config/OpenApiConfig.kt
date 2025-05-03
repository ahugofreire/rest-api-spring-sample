package com.rest_api.spring.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun customOpenApi(): OpenAPI {
        return OpenAPI().info(
            // Link http://localhost:8080/swagger-ui/index.html
            Info()
                .title("RESTful API with Kotlin and Spring Boot 3")
                .version("v1")
                .description("Some description about your API.")
                .termsOfService("https://pub.erudio.com.br/meus-cursos")
                .license(
                    License().name("Apache 2")
                        .url("https://pub.erudio.com.br/meus-cursos")
                )
        )
    }
}