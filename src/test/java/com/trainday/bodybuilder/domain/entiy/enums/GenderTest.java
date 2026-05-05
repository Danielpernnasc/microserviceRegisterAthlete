package com.trainday.bodybuilder.domain.entiy.enums;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.trainday.bodybuilder.domain.model.enums.Gender;

public class GenderTest {

    @Test
    void shouldReturnGender_whenValidValue(){
        Gender result = Gender.forValue("Homem");

        assertEquals(Gender.MALE, result);
    }

    @Test
    void shouldIgnoreCase_whenValidValue(){
        Gender result = Gender.forValue("Mulher");

        assertEquals(Gender.FEMALE, result);
    }

    @Test
    void shouldThrowException_whenInvalidValue(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Gender.forValue("invalid");
        });
        assertEquals("Gender invalid: invalid", exception.getMessage());
    }

    @Test
    void shouldReturnState_whenGetState(){
        String result = Gender.MALE.getState();
        assertEquals("Homem", result);
    }



}
