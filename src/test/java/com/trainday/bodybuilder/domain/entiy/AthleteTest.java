package com.trainday.bodybuilder.domain.entiy;

import static org.junit.jupiter.api.Assertions.*;

import com.trainday.bodybuilder.domain.model.enums.Role;
import org.junit.jupiter.api.Test;

import com.trainday.bodybuilder.domain.model.Athlete;
import com.trainday.bodybuilder.domain.model.enums.Gender;
import com.trainday.bodybuilder.domain.model.enums.GenderIdentity;

public class AthleteTest {


    @Test
    void shouldTestCreateAthlete() {
        Athlete athlete = new Athlete(
            "1",
            "999.999.999-99",
            "Daniel Péricles do Nascimento",
            "dpericles6@gmail.com",
            45L,
            Gender.MALE,
            GenderIdentity.CISGENDER,
            1.82,
            105.40,
            "user-1",
                Role.ATHLETE
        );


        assertEquals("1", athlete.getId());
        assertEquals("999.999.999-99", athlete.getCpf());
        assertEquals("Daniel Péricles do Nascimento", athlete.getName());
        assertEquals("dpericles6@gmail.com", athlete.getEmail());
        assertEquals(45L, athlete.getAge());
        assertEquals(Gender.MALE, athlete.getGender());
        assertEquals(GenderIdentity.CISGENDER, athlete.getIdentity());
        assertEquals(1.82, athlete.getHeight());
        assertEquals(105.40, athlete.getWeight());
        assertEquals("user-1", athlete.getUserId());
    }

    @Test
    void shouldSetAthleteData() {
        Athlete athlete = new Athlete();

        athlete.setId("2");
        athlete.setCpf("12345678900");
        athlete.setName("Teste");
        athlete.setEmail("teste@email.com");
        athlete.setAge(30L);
        athlete.setGender(Gender.MALE);
        athlete.setIdentity(GenderIdentity.CISGENDER);
        athlete.setHeight(1.75);
        athlete.setWeight(80.0);
        athlete.setUserId("user-2");

        assertEquals("2", athlete.getId());
        assertEquals("12345678900", athlete.getCpf());
        assertEquals("Teste", athlete.getName());
        assertEquals("teste@email.com", athlete.getEmail());
        assertEquals(30L, athlete.getAge());
        assertEquals(Gender.MALE, athlete.getGender());
        assertEquals(GenderIdentity.CISGENDER, athlete.getIdentity());
        assertEquals(1.75, athlete.getHeight());
        assertEquals(80.0, athlete.getWeight());
        assertEquals("user-2", athlete.getUserId());

        
    }
}


