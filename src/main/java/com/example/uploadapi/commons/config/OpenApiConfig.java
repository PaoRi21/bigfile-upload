package com.example.uploadapi.commons.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Clase de configuración para OpenAPI.
 * Define la configuración de la documentación de la API, incluyendo información general y esquemas de seguridad.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Bean que configura la instancia personalizada de OpenAPI.
     * Incluye información sobre la API y el esquema de seguridad para autenticación con JWT.
     *
     * @return una instancia de {@link OpenAPI} con la configuración personalizada.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Carga de Archivos") // Título de la API.
                        .version("1.0") // Versión de la API.
                        .description("Servicio para autenticación y carga de archivos grandes")) // Descripción de la API.
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", // Nombre del esquema de seguridad.
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP) // Tipo de esquema de seguridad (HTTP).
                                        .scheme("bearer") // Esquema utilizado (Bearer).
                                        .bearerFormat("JWT"))) // Formato del token (JWT).
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth")); // Requisito de seguridad para la API.
    }
}