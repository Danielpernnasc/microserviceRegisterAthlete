package com.trainday.bodybuilder.api.DTO.request;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.trainday.bodybuilder.domain.model.enums.Gender;
import com.trainday.bodybuilder.domain.model.enums.GenderIdentity;

public class AthleteRequestTest {

    @Test
    void shouldAthleteRequest(){

        AthleteRequest athleteRequest = new AthleteRequest(
            "999.999.999-99",
            "Daniel Péricles do Nascimento",
            "dpericles6@gmail.com",
            45L,
            Gender.MALE,
            GenderIdentity.CISGENDER,
            182.5,
            105.5
        );
        assertEquals("999.999.999-99", athleteRequest.cpf());
        assertEquals("Daniel Péricles do Nascimento", athleteRequest.name());
        assertEquals("dpericles6@gmail.com", athleteRequest.email());
        assertEquals(45L, athleteRequest.age());
        assertEquals(Gender.MALE, athleteRequest.gender());
        assertEquals(GenderIdentity.CISGENDER, athleteRequest.identity());
        assertEquals(182.5, athleteRequest.height());
        assertEquals(105.5, athleteRequest.weight());

        
    }

}
