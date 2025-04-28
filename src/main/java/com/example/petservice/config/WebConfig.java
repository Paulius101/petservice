package com.example.petservice.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Enable CORS for all endpoints and allow requests from localhost:4200
        registry.addMapping("/**")  // This allows CORS for all API endpoints
                .allowedOrigins("http://localhost:4200")  // Only allow your frontend (Angular) to make requests
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE")  // Define allowed HTTP methods
                .allowedHeaders("*");  // Allow all headers
    }
}
