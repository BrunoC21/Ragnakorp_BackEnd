package com.Polo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/proyecto/**")
                .allowedOrigins("http://localhost:5500", "http://localhost:8080")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
                .allowCredentials(true);

        registry.addMapping("/postulation/**")
                .allowedOrigins("http://localhost:5500", "http://localhost:8080")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
                .allowCredentials(true);

        registry.addMapping("/user/**")
                .allowedOrigins("http://localhost:5500", "http://localhost:8080")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
                .allowCredentials(true);

        registry.addMapping("/news/**")
                .allowedOrigins("http://localhost:5500", "http://localhost:8080")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
                .allowCredentials(true);
        
        registry.addMapping("/proyecto/user/login")
                .allowedOrigins("http://localhost:5500", "http://localhost:8080")
                .allowedMethods("POST", "GET", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
                .allowCredentials(true);
                
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5500")  // Dirección de tu frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true)  // Permitir el envío de cookies
                .allowedHeaders("*");

        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5500") // Asegúrate de colocar la URL correcta de tu frontend
                .allowCredentials(true);
    }
}
