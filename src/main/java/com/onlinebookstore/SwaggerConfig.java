package com.onlinebookstore;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Online Bookstore API", version = "1.0", description = "API documentation for the Online Bookstore application"))
public class SwaggerConfig {
}
