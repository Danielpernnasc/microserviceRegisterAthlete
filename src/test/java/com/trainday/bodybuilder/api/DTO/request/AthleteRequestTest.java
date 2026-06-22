package com.trainday.bodybuilder.api.DTO.request;

import static org.junit.jupiter.api.Assertions.*;

import com.trainday.bodybuilder.domain.model.enums.Role;
import org.junit.jupiter.api.Test;

import com.trainday.bodybuilder.domain.model.enums.Gender;
import com.trainday.bodybuilder.domain.model.enums.GenderIdentity;

import java.time.LocalDate;

public class AthleteRequestTest {

    @Test
    void shouldAthleteRequest(){

        AthleteRequest athleteRequest = new AthleteRequest(
            "Daniel Péricles do Nascimento",
            null,
            Gender.MALE,
            GenderIdentity.CISGENDER,
            182.5,
            105.5

        );

        assertEquals("Daniel Péricles do Nascimento", athleteRequest.name());
        assertEquals(null, athleteRequest.socialName());
        assertEquals(Gender.MALE, athleteRequest.gender());
        assertEquals(GenderIdentity.CISGENDER, athleteRequest.identity());
        assertEquals(182.5, athleteRequest.height());
        assertEquals(105.5, athleteRequest.weight());


        
    }

}
