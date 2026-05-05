package com.trainday.bodybuilder.api.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import com.trainday.bodybuilder.api.DTO.request.LoginRequest;
import com.trainday.bodybuilder.api.DTO.response.LoginResponse;
import com.trainday.bodybuilder.application.service.LoginService;


@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {


    @Mock
    LoginService loginservice;

    @InjectMocks
    AuthController authController;

    @Test
    void shouldRegister(){
        LoginRequest loginreq = new LoginRequest(
                "dpericles6@gmail.com",
                "123456"
        );


        LoginResponse loginResponse = new LoginResponse(
              "user-1",
              "dpericles6@gmail.com"
    
        );

        when(loginservice.createLogin(loginreq)).thenReturn(loginResponse);

        LoginResponse created = authController.register(loginreq);

        assertNotNull(created);
        assertEquals("user-1", created.id());
        assertEquals("dpericles6@gmail.com", created.email());

        verify(loginservice).createLogin(loginreq);
    }

    @Test
    void shouldLogin(){

          LoginRequest loginreq = new LoginRequest(
                "dpericles6@gmail.com",
                "123456"
        );

        when(loginservice.authenticate(loginreq)).thenReturn("meu-token");

        ResponseEntity<?> response = authController.login(loginreq);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Map<String, String> body = (Map<String, String>) response.getBody();
        assertNotNull(body);
        assertEquals("meu-token", body.get("token"));

        verify(loginservice).authenticate(loginreq);

    }

    



}
