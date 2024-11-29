package com.Polo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                .requestMatchers("/user/login", "/user/create", "/user/search", "/user/search/rut/**", "/user/assignRole/**", "/project/create/**", "/news/create/**", "/project/search", "/user/sessionInfo", "/news/search", "/news/create", "/environmentVinculation/create", "/environmentVinculation/search", "/news/update", "/environmentVinculation/update", "/project/create", "/postulation/create", "/suscriptor/unsubscribe", "/suscriptor/create", "/postulation/search", "/postulation/update", "/userProj/myproj", "/userProj/myprojCreate").permitAll() // Permitir acceso sin autenticación
                .anyRequest().authenticated()
                )
                .logout(logout -> logout
                        .logoutUrl("/user/logout")
                        .logoutSuccessUrl("/user/login?logout=true")
                        .permitAll())
                .sessionManagement(session -> session
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true))
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
