package com.Polo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // Deshabilitar CSRF si usas tokens (JWT) o frontend separado
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/user/login", "/user/create").permitAll()  // Permitir acceso público a /login y /create
                .anyRequest().authenticated()  // Proteger todas las demás rutas
            )
            .formLogin(form -> form
                .loginProcessingUrl("/user/login")  // Ruta para el login
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/user/logout")
                .logoutSuccessUrl("/user/login?logout=true")  // Redirigir al login tras cerrar sesión
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
