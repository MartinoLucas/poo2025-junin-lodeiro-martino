package com.poo.proyecto.config;

import com.poo.proyecto.security.jwt.JwtAuthorizationFilter;
import com.poo.proyecto.service.AuthorizationService;
import com.poo.proyecto.util.JwtTokenUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenUtil jwtTokenUtil;

    public SecurityConfig(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(
                        new JwtAuthorizationFilter(jwtTokenUtil),
                        UsernamePasswordAuthenticationFilter.class
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/accounts").permitAll()

                        // solo admin
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // resto requiere ADMIN o PARTICIPANTE
                        .requestMatchers("/**").hasAnyRole("ADMIN", "PARTICIPANTE")
                )
        ;

        return http.build();
    }
}
