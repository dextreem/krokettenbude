package com.dextreem.croqueteria.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.servers.Server
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
@OpenAPIDefinition(
    info = Info(
        title = "Croqueteria - API Documentation",
        version = "v1",
        description = "Endpoints for Croqueteria API, a service that allows to describe, rate and discuss the most delicious croquettes around the globe."
    ),
    security = [SecurityRequirement(name = "bearerAuth")]
)
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT"
)
class SwaggerConfig{
    @Value("\${swagger.server.url:}")
    private val swaggerUrl: String? = null

    @Bean
    fun customOpenAPI(): OpenAPI? {
        println("swaggerUrl=$swaggerUrl")
        return if (!swaggerUrl.isNullOrBlank()) {
            OpenAPI().addServersItem(Server().url(swaggerUrl))
        } else {
            null
        }
    }

}
