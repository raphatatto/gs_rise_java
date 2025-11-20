package br.com.fiap.gs_rise.config;

import br.com.fiap.gs_rise.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // API REST, sem sessão
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(auth -> auth
                        // Swagger / docs / actuator: públicos (ajusta se tiver isso)
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/actuator/**"
                        ).permitAll()

                        // cadastro de usuário: público
                        .requestMatchers(HttpMethod.POST, "/api/v1/usuarios").permitAll()
                        .requestMatchers("/api/v1/ia/**").authenticated()

                         .requestMatchers("/health").permitAll()

                        // Endpoints de leitura de cursos: qualquer usuário autenticado
                        .requestMatchers(HttpMethod.GET, "/api/v1/cursos/**").authenticated()

                        // Exemplo: endpoints de currículos e trilhas: autenticados
                        .requestMatchers("/api/v1/curriculos/**", "/api/v1/trilhas/**", "/api/v1/objetivos/**").authenticated()

                        // Qualquer outra coisa: autenticado
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()); // autenticação HTTP Basic

        return http.build();
    }
}
