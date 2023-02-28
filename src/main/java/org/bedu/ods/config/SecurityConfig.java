package org.bedu.ods.config;

import lombok.RequiredArgsConstructor;
import org.bedu.ods.config.jwt.JwtTokenAuthenticationFilter;
import org.bedu.ods.config.jwt.JwtTokenProvider;
import org.bedu.ods.service.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static java.lang.String.format;

/**
 * Security configuration for the main application.
 *
 * @author Josh Cummings
 */
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    @Value("${springdoc.api-docs.path}")
    private String restApiDocPath;

    @Value("${springdoc.swagger-ui.path}")
    private String swaggerPath;

    private final JpaUserDetailsService jpaUserService;

    @Bean
    SecurityFilterChain springWebFilterChain(HttpSecurity http,
                                             JwtTokenProvider tokenProvider) throws Exception {
        return http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(c -> c.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(new AntPathRequestMatcher("/token"))
                            .authenticated()
                        .requestMatchers(format("%s/**", restApiDocPath))
                            .permitAll()
                        .requestMatchers(format("%s/**", swaggerPath))
                            .permitAll()
                        .requestMatchers("/api/all").permitAll()
                        .requestMatchers("/auth/signin").permitAll()
                        .requestMatchers("/api/usuarios/registro").permitAll()
                        .requestMatchers("/api/usuario").hasAnyRole("USER", "ROLE_USER")
                        .requestMatchers("/api/manager").hasAnyRole("MANAGER", "ROLE_MANAGER")
                        .requestMatchers("/api/admin").hasAnyRole("ADMIN", "ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/usuarios/**").hasAnyRole("ADMIN" ,"ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/proyectos/**").hasAnyRole("ADMIN" ,"MANAGER", "ROLE_ADMIN", "ROLE_MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/tareas/**").hasAnyRole( "MANAGER", "USER", "ROLE_MANAGER", "ROLE_USER")
                        .requestMatchers("/api/**").authenticated()
                )
                .addFilterBefore(new JwtTokenAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
                .userDetailsService(jpaUserService)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .build();
    }

    // Set password encoding schema
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
