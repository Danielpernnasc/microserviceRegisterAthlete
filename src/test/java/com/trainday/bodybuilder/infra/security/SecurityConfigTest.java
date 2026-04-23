package com.trainday.bodybuilder.infra.security;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.mongodb.autoconfigure.MongoAutoConfiguration;
import org.springframework.boot.data.mongodb.autoconfigure.DataMongoAutoConfiguration;
import org.springframework.boot.data.mongodb.autoconfigure.DataMongoRepositoriesAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.trainday.bodybuilder.api.controller.AuthController;
import com.trainday.bodybuilder.application.service.LoginService;

import jakarta.servlet.Filter;
import jakarta.servlet.ServletException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SecurityConfigTest.TestBeans.class)
@WebAppConfiguration
@TestPropertySource(properties = {
    "jwt.secret=chave-falsa-para-teste-de-seguranca-2026",
    "jwt.expiration=3600000"
})
public class SecurityConfigTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private Filter springSecurityFilterChain;

    @Autowired
    private LoginService loginService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilters(springSecurityFilterChain)
                .build();
    }

    @Test
    void loginDeveSerPublico() throws Exception {
        when(loginService.authenticate(any())).thenReturn("token-test");

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"user@test.com\",\"password\":\"123456\"}"))
                .andExpect(status().isOk());
    }

    @TestConfiguration
    @EnableWebMvc
    @EnableWebSecurity
    @Import(SecurityConfig.class)
    @EnableAutoConfiguration(exclude = {
        MongoAutoConfiguration.class,
        DataMongoAutoConfiguration.class,
        DataMongoRepositoriesAutoConfiguration.class
    })
    static class TestBeans {

        @Bean
        AuthController authController(LoginService loginService) {
            return new AuthController(loginService);
        }

        @Bean
        LoginService loginService() {
            return mock(LoginService.class);
        }

        @Bean
        JwtService jwtService() {
            JwtService jwtService = new JwtService();
            org.springframework.test.util.ReflectionTestUtils.setField(
                jwtService, "secret", "chave-falsa-para-teste-de-seguranca-2026");
            org.springframework.test.util.ReflectionTestUtils.setField(
                jwtService, "expiration", 3600000L);
            return jwtService;
        }

        @Bean
        JwtAuthFilter jwtAuthFilter() {
            JwtAuthFilter mock = mock(JwtAuthFilter.class);
            try {
                doAnswer(inv -> {
                    try {
                        jakarta.servlet.FilterChain chain = inv.getArgument(2);
                        chain.doFilter(inv.getArgument(0), inv.getArgument(1));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    return null;
                }).when(mock).doFilter(any(), any(), any());
            } catch (ServletException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return mock;
        }
    }
}