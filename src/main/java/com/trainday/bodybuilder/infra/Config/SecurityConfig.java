package com.trainday.bodybuilder.infra.Config;

import static org.springframework.security.web.util.matcher.RegexRequestMatcher.regexMatcher;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

import com.trainday.bodybuilder.infra.security.JwtAuthFilter;

@Configuration
public class SecurityConfig {
     private static final String ATHLETE_PATH = "/athlete/*";
     private static final String ATHLETE = "/athlete";
     private static final String ATHLETE_PATCH_CPF = "/athlete/**";
     private static final String AUTH = "/auth/*";
     private static final String HEALTYHISTORY = "/HealtyHistory";
     private static final String HEALTYHISTORY_PATH = "/HealtyHistory/**";


    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter){

        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain swaggerFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher(swaggerRequestMatcher())
                .csrf(csrf -> csrf.disable()) // Safe: application is stateless and uses JWT tokens in Authorization header (no cookies)
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
            return http
                    .csrf(csrf -> csrf.disable()) // Safe: application is stateless and uses JWT tokens in Authorization header (no cookies)
                    .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                   .authorizeHttpRequests(auth -> auth
                           .requestMatchers(HttpMethod.POST,  HEALTYHISTORY ).permitAll()
                           .requestMatchers(HttpMethod.GET, HEALTYHISTORY_PATH).authenticated()
                           .requestMatchers(HttpMethod.PUT, HEALTYHISTORY_PATH).authenticated()
                           .requestMatchers(HttpMethod.PATCH, HEALTYHISTORY_PATH).authenticated()
                    .requestMatchers(HttpMethod.POST, AUTH).permitAll()
                    .requestMatchers(HttpMethod.POST, AUTH).permitAll()
                   .requestMatchers(HttpMethod.GET, ATHLETE_PATH).authenticated()
                   .requestMatchers(HttpMethod.PUT, ATHLETE_PATH).authenticated()
                   .requestMatchers(HttpMethod.DELETE, ATHLETE_PATH).authenticated()
                   .requestMatchers(HttpMethod.PATCH, ATHLETE_PATH).authenticated()
                   .requestMatchers(HttpMethod.GET, ATHLETE_PATCH_CPF).permitAll()
                   .requestMatchers(HttpMethod.POST, ATHLETE).authenticated()


                    .anyRequest().authenticated()
                )

                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

  }


    private OrRequestMatcher swaggerRequestMatcher() {
        return new OrRequestMatcher(
            regexMatcher("^/v3/api-docs(/.*)?$"),
            regexMatcher("^/swagger-ui(/.*)?$"),
            regexMatcher("^/swagger-ui\\.html$"),
            regexMatcher("^/swagger-resources(/.*)?$"),
            regexMatcher("^/webjars(/.*)?$")
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return (AuthenticationManager) config.getAuthenticationManager();
    }

 

}
