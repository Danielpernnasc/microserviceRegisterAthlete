package com.trainday.bodybuilder.domain.entiy;

import static org.junit.jupiter.api.Assertions.*;

import com.trainday.bodybuilder.domain.model.enums.Role;
import org.junit.jupiter.api.Test;

import com.trainday.bodybuilder.domain.model.Athlete;
import com.trainday.bodybuilder.domain.model.enums.Gender;
import com.trainday.bodybuilder.domain.model.enums.GenderIdentity;

import java.time.LocalDate;

public class AthleteTest {


    @Test
    void shouldTestCreateAthlete() {
        Athlete athlete = new Athlete(
            "1",
            "999.999.999-99",
            "Daniel Péricles do Nascimento",
            null,
            "dpericles6@gmail.com",
            LocalDate.of(2000,1,1),
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
        assertEquals( LocalDate.of(2000,01,01), athlete.getBorn());
        assertEquals(Gender.MALE, athlete.getGender());
        assertEquals(GenderIdentity.CISGENDER, athlete.getIdentity());
        assertEquals(1.82, athlete.getHeight());
        assertEquals(105.40, athlete.getWeight());
        assertEquals(Role.ATHLETE, athlete.getRole());
        assertEquals("user-1", athlete.getUserId());
    }

    @Test
    void shouldSetAthleteData() {
        Athlete athlete = new Athlete();

        athlete.setId("2");
        athlete.setCpf("12345678900");
        athlete.setName("Teste");
        athlete.setSocialname(null);
        athlete.setEmail("teste@email.com");
        athlete.setBorn(LocalDate.of(2000, 1,1));
        athlete.setGender(Gender.MALE);
        athlete.setIdentity(GenderIdentity.CISGENDER);
        athlete.setHeight(1.75);
        athlete.setWeight(80.0);
        athlete.setRole(Role.ATHLETE);
        athlete.setUserId("user-2");

        assertEquals("2", athlete.getId());
        assertEquals("12345678900", athlete.getCpf());
        assertEquals("Teste", athlete.getName());
        assertEquals(null, athlete.getSocialname());
        assertEquals("teste@email.com", athlete.getEmail());
        assertEquals(LocalDate.of(2000,1,1), athlete.getBorn());
        assertEquals(Gender.MALE, athlete.getGender());
        assertEquals(GenderIdentity.CISGENDER, athlete.getIdentity());
        assertEquals(1.75, athlete.getHeight());
        assertEquals(80.0, athlete.getWeight());
        assertEquals(Role.ATHLETE, athlete.getRole());
        assertEquals("user-2", athlete.getUserId());

        
    }
}


