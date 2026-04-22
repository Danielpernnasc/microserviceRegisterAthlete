package com.trainday.bodybuilder.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.trainday.bodybuilder.api.DTO.request.AthleteRequest;
import com.trainday.bodybuilder.api.DTO.response.AthleteResponse;
import com.trainday.bodybuilder.application.service.AthleteService;
import com.trainday.bodybuilder.domain.model.Athlete;

@ExtendWith(MockitoExtension.class)
public class AthleteControllerTest {

    @Mock
    AthleteService athleteservice;

    @InjectMocks
    AthleteController athleteController;

    @Test
    void shouldSaveAhtlete() {
        AthleteRequest athleteReq = new AthleteRequest(
            "99999999999",
            "Daniel Péricles do Nascimento",
            "dpericles6@gmail.com",
            45L,
            182.5,
            105.5,
            18L
        );

        Athlete athlete = new Athlete();
        athlete.setCPF("99999999999");
        athlete.setName("Daniel Péricles do Nascimento");
        athlete.setEmail("dpericles6@gmail.com");
        athlete.setAge(45L);
        athlete.setHeight(182.5);
        athlete.setWeight(105.5);
        athlete.setpercentageFat(18L);

        when(athleteservice.createAthlete(athleteReq)).thenReturn(athlete);

        Athlete created = athleteController.save(athleteReq);

        assertNotNull(created);
        assertEquals(athleteReq.cpf(), created.getCPF());
        assertEquals(athleteReq.name(), created.getName());
        assertEquals(athleteReq.email(), created.getEmail());
        assertEquals(athleteReq.age(), created.getAge());
        assertEquals(athleteReq.height(), created.getHeight());
        assertEquals(athleteReq.weight(), created.getWeight());
        assertEquals(athleteReq.percentagefat(), created.getpercentageFat());

        verify(athleteservice).createAthlete(athleteReq);
    }

    @Test
    void shouldFindByid() {
        AthleteResponse athleteResponse = new AthleteResponse(
            "1",
            "99999999999",
            "Daniel Péricles do Nascimento",
            "dpericles6@gmail.com",
            45L,
            182.5,
            105.5,
            18L
        );

        when(athleteservice.getAthleteById("1"))
            .thenReturn(athleteResponse);

          AthleteResponse result = athleteController.findById("1");
    

            assertEquals("1", result.id());
            assertEquals("99999999999", result.cpf());
            assertEquals("Daniel Péricles do Nascimento", result.name());
            assertEquals("dpericles6@gmail.com", result.email());
            assertEquals(45L, result.age());
            assertEquals(182.5, result.height());
            assertEquals(105.5, result.weight());
            assertEquals(18L, result.percentagefat());

             verify(athleteservice).getAthleteById("1");
    }

    @Test
    void shouldUpdateAthlete(){
        AthleteRequest athleteReq = new AthleteRequest(
            "99999999999",
            "Daniel Péricles do Nascimento",
            "dpericles6@gmail.com",
            45L,
            182.5,
            105.5,
            18L
        );

        Athlete athlete = new Athlete();
        athlete.setCPF("99999999999");
        athlete.setName("Daniel Péricles do Nascimento");
        athlete.setEmail("dpericles6@gmail.com");
        athlete.setAge(45L);
        athlete.setHeight(182.5);
        athlete.setWeight(105.5);
        athlete.setpercentageFat(18L);

        when(athleteservice.updateAthlete("1", athleteReq))
            .thenReturn(athlete);
        Athlete updated = athleteController.updateAthlete("1", athleteReq);
        

        assertNotNull(updated);
        assertEquals("99999999999", athleteReq.cpf());
        assertEquals("Daniel Péricles do Nascimento", athleteReq.name());
        assertEquals("dpericles6@gmail.com", athleteReq.email());
        assertEquals(45L, athleteReq.age());
        assertEquals(182.5, athleteReq.height());
        assertEquals(105.5, athleteReq.weight());
        assertEquals(18L, athleteReq.percentagefat());
        
        verify(athleteservice).updateAthlete("1",athleteReq);

    }

    @Test
    void shouldDeleteAthlete(){
          Athlete existAthlete = new Athlete();
            existAthlete.setId("1");
            existAthlete.setCPF("999.999.999-99");
            existAthlete.setName("Daniel Péricles do Nascimento");
            existAthlete.setAge(45L);
            existAthlete.setEmail("dpericles6@gmail.com");
            existAthlete.setHeight(181.90);
            existAthlete.setWeight(105.10);
            existAthlete.setpercentageFat(15L);
            existAthlete.setUserId("user-1");
    

          doNothing().when(athleteservice).deleteAthlete("1");
          
          athleteController.deleteAhtlete("1");

          verify(athleteservice).deleteAthlete("1");

    }
   
}
