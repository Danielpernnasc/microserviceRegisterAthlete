package com.trainday.bodybuilder.domain.entiy.enums;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.trainday.bodybuilder.domain.model.enums.Gender;

public class GenderTest {

    @Test
    void shouldReturnGender_whenValidValue(){
        Gender result = Gender.forValue("Homem");
        Gender result2 = Gender.forValue("Mulher");

        assertEquals(Gender.MALE, result);
        assertEquals(Gender.FEMALE, result2);

    }

    @Test
    void shouldIgnoreCase_whenValidValue(){

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
