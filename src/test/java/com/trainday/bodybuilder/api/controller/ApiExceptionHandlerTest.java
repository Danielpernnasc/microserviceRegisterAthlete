package com.trainday.bodybuilder.api.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.trainday.bodybuilder.application.service.AthleteCpfAlreadyExistsException;

public class ApiExceptionHandlerTest {

    @Test
    void shouldHandleAthleteCpfAlreadyExistsException() {
        ApiExceptionHandler handler = new ApiExceptionHandler();
        AthleteCpfAlreadyExistsException exception = new AthleteCpfAlreadyExistsException("12345678900");
        
        ResponseEntity<Map<String, String>> response = handler.handleAthleteCpfAlreadyExists(exception);
        
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().containsKey("message"));
        assertTrue(response.getBody().get("message").contains("CPF already exists"));
    }

    @Test
    void shouldReturnCorrectMessageFormat() {
        ApiExceptionHandler handler = new ApiExceptionHandler();
        String testCpf = "12345678900";
        AthleteCpfAlreadyExistsException exception = new AthleteCpfAlreadyExistsException(testCpf);
        
        ResponseEntity<Map<String, String>> response = handler.handleAthleteCpfAlreadyExists(exception);
        
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().get("message"));
        assertTrue(response.getBody().get("message").startsWith("CPF already exists"));
        assertTrue(response.getBody().get("message").contains(testCpf));
    }

    @Test
    void shouldReturnHttpStatusConflict() {
        ApiExceptionHandler handler = new ApiExceptionHandler();
        AthleteCpfAlreadyExistsException exception = new AthleteCpfAlreadyExistsException("99999999999");
        
        ResponseEntity<Map<String, String>> response = handler.handleAthleteCpfAlreadyExists(exception);
        
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    void shouldReturnMessageInResponseBody() {
        ApiExceptionHandler handler = new ApiExceptionHandler();
        AthleteCpfAlreadyExistsException exception = new AthleteCpfAlreadyExistsException("11122233344");
        
        ResponseEntity<Map<String, String>> response = handler.handleAthleteCpfAlreadyExists(exception);
        
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertTrue(response.getBody().containsKey("message"));
        assertEquals("CPF already exists: 11122233344", response.getBody().get("message"));
    }
}
