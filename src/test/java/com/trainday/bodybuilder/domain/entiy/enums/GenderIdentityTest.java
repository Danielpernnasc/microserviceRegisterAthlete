package com.trainday.bodybuilder.domain.entiy.enums;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.trainday.bodybuilder.domain.model.enums.Gender;
import com.trainday.bodybuilder.domain.model.enums.GenderIdentity;

public class GenderIdentityTest {

    @Test
    void shouldReturnGIdentity_whenValidValue(){
        GenderIdentity result = GenderIdentity.formValue("Cisgênero");
        assertEquals(GenderIdentity.CISGENDER, result);
    }

    @Test
    void shouldIgnoreCase_whenValidValue(){
        GenderIdentity result = GenderIdentity.formValue("Transgênero");
        assertEquals(GenderIdentity.TRANSGENDER, result);
    }

    @Test
    void shouldThrowsException_whenValideValue(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Gender.forValue("invalid");
        });
        assertEquals("Gender invalid: invalid", exception.getMessage());
    }

    @Test
    void shouldReturnState_whenGetState(){
        String result = GenderIdentity.CISGENDER.getState();
        assertEquals("Cisgênero", result);
    }

}
