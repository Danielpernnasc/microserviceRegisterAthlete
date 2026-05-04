package com.trainday.bodybuilder.api.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.trainday.bodybuilder.application.service.AthleteCpfAlreadyExistsException;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(AthleteCpfAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleAthleteCpfAlreadyExists(
            AthleteCpfAlreadyExistsException ex
    ) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of("message", ex.getMessage()));
    }
}
