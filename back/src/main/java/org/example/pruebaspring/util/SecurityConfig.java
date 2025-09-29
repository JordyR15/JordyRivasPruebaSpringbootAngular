package org.example.pruebaspring.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults; // <-- 1. IMPORTA ESTO

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 2. APLICA LA CONFIGURACIÓN DE CORS DE WebConfig
                .cors(withDefaults())

                .csrf().disable()
                .authorizeHttpRequests(authz -> authz
                        // 3. PERMITE LAS PETICIONES PREFLIGHT (OPTIONS) DEL NAVEGADOR
                        .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // 4. AÑADE TUS RUTAS DE SESIÓN A LA LISTA BLANCA
                        .antMatchers(
                                "/api/session/**", // <-- CUBRE LOGIN Y CAMBIAR CONTRASEÑA
                                "/api/auth/**",
                                "/api/roles/list",
                                "/api/peliculas/**",
                                "/api/salas/**"
                        ).permitAll()
                        .antMatchers(HttpMethod.POST, "/api/users").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}