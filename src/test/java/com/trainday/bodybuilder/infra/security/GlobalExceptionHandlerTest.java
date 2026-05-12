package com.trainday.bodybuilder.infra.security;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;

public class GlobalExceptionHandlerTest {
        private final GlobalExceptionHandler handler = new GlobalExceptionHandler();
        
           @Test
           void  shouldHandleBadCredentialsException() {

                BadCredentialsException exception =
                        new BadCredentialsException("Invalid email or password");

                ResponseEntity<String> response =
                        handler.handleBadCredentials(exception);

                assertNotNull(response);
                assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
                assertEquals(
                        "Invalid email or password",
                        response.getBody()
                );
             }

}
