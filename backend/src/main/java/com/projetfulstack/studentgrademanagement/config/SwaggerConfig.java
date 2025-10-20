package com.projetfulstack.studentgrademanagement.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Swagger/OpenAPI Configuration
 * Configures API documentation with Swagger UI
 * 
 * @author ProjetFullStack
 * @version 1.0.0
 */
@Configuration
public class SwaggerConfig {

    @Value("${server.port:8080}")
    private String serverPort;

    @Value("${server.servlet.context-path:/api}")
    private String contextPath;

    @Bean
    public OpenAPI customOpenAPI() {
        String serverUrl = "http://localhost:" + serverPort + contextPath;
        
        return new OpenAPI()
                .info(new Info()
                        .title("Student Grade Management System API")
                        .description("""
                                RESTful API for managing student grades, subjects, and academic records.
                                
                                ## Features
                                - 🔐 JWT Authentication & Authorization
                                - 👥 Student Management
                                - 📝 Grade Management
                                - 📚 Subject Management
                                - 👨‍🏫 Teacher Management
                                - 📊 Dashboard & Reports
                                - 📧 Email Notifications
                                
                                ## Authentication
                                Use the **Authorize** button to authenticate with JWT token.
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("ProjetFullStack Team")
                                .email("contact@projetfulstack.com")
                                .url("https://projetfulstack.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url(serverUrl)
                                .description("Development Server")
                ))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("""
                                        JWT Authorization header using the Bearer scheme.
                                        
                                        Example: `Authorization: Bearer <token>`
                                        
                                        To get a token:
                                        1. Use the `/auth/login` endpoint
                                        2. Copy the token from the response
                                        3. Click the 'Authorize' button
                                        4. Enter: `Bearer <your-token>`
                                        """)));
    }
}
