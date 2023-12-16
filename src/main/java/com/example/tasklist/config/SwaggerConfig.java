package com.example.tasklist.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
            .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
            .components(
                    new Components().addSecuritySchemes("bearerAuth", new SecurityScheme()
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer").bearerFormat("JWT")
                    )

            )
            .info(new Info()
                    .title("Task List API")
                    .description("Demo Spring boot application")
                    .version("1.0")
                    .contact(new Contact()
                            .name("Miroslav Kosiuk")
                            .email("miroslavkosiuk@gmail")
                    )
                    .termsOfService("Bla Bla Bla")
            );
  }
}
