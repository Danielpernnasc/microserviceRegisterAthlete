package com.trainday.bodybuilder.domain.entiy;

import static org.junit.jupiter.api.Assertions.*;

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
            Gender.Male,
            GenderIdentity.CISGENDER,
            1.82,
            105.40,
            12L,
            "user-1"
        );

        assertEquals("1", athlete.getId());
        assertEquals("999.999.999-99", athlete.getCPF());
        assertEquals("Daniel Péricles do Nascimento", athlete.getName());
        assertEquals("dpericles6@gmail.com", athlete.getEmail());
        assertEquals(45L, athlete.getAge());
        assertEquals(Gender.Male, athlete.getGender());
        assertEquals(GenderIdentity.CISGENDER, athlete.getIdentity());
        assertEquals(1.82, athlete.getHeight());
        assertEquals(105.40, athlete.getWeight());
        assertEquals(12L, athlete.getpercentageFat());
        assertEquals("user-1", athlete.getUserId());
    }

    @Test
    void shouldSetAthleteData() {
        Athlete athlete = new Athlete();

        athlete.setId("2");
        athlete.setCPF("12345678900");
        athlete.setName("Teste");
        athlete.setEmail("teste@email.com");
        athlete.setAge(30L);
        athlete.setGender(Gender.Male);
        athlete.setIdentity(GenderIdentity.CISGENDER);
        athlete.setHeight(1.75);
        athlete.setWeight(80.0);
        athlete.setpercentageFat(10L);
        athlete.setUserId("user-2");

        assertEquals("2", athlete.getId());
        assertEquals("12345678900", athlete.getCPF());
        assertEquals("Teste", athlete.getName());
        assertEquals("teste@email.com", athlete.getEmail());
        assertEquals(30L, athlete.getAge());
        assertEquals(Gender.Male, athlete.getGender());
        assertEquals(GenderIdentity.CISGENDER, athlete.getIdentity());
        assertEquals(1.75, athlete.getHeight());
        assertEquals(80.0, athlete.getWeight());
        assertEquals(10L, athlete.getpercentageFat());
        assertEquals("user-2", athlete.getUserId());

        
    }
}


