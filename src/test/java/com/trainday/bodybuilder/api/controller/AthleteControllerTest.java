package com.trainday.bodybuilder.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.trainday.bodybuilder.api.DTO.request.AthleteRequest;
import com.trainday.bodybuilder.api.DTO.response.AthleteResponse;
import com.trainday.bodybuilder.application.service.AthleteService;
import com.trainday.bodybuilder.domain.model.Athlete;
import com.trainday.bodybuilder.domain.model.enums.Gender;
import com.trainday.bodybuilder.domain.model.enums.GenderIdentity;
import com.trainday.bodybuilder.domain.repository.AthleteRepository;

@ExtendWith(MockitoExtension.class)
public class AthleteControllerTest {

    @Mock
    AthleteService athleteservice;

    @Mock
    AthleteRepository athleteRepository;

    @InjectMocks
    AthleteController athleteController;

    @Test
    void shouldSaveAhtlete() {
        AthleteRequest athleteReq = new AthleteRequest(
            "123.456.789-00",
            "Daniel Péricles do Nascimento",
            "dpericles6@gmail.com",
            45L,
            Gender.MALE,
            GenderIdentity.CISGENDER,
            182.5,
            105.5
        );

        Athlete athlete = new Athlete();
        athlete.setCpf("123.456.789-00");
        athlete.setName("Daniel Péricles do Nascimento");
        athlete.setEmail("dpericles6@gmail.com");
        athlete.setAge(45L);
        athlete.setGender(Gender.MALE);
        athlete.setIdentity(GenderIdentity.CISGENDER);
        athlete.setHeight(182.5);
        athlete.setWeight(105.5);

        when(athleteservice.createAthlete(athleteReq)).thenReturn(athlete);

        Athlete created = athleteController.save(athleteReq);

        assertNotNull(created);
        assertEquals(athleteReq.cpf(), created.getCpf());
        assertEquals(athleteReq.name(), created.getName());
        assertEquals(athleteReq.email(), created.getEmail());
        assertEquals(athleteReq.age(), created.getAge());
        assertEquals(athleteReq.gender(), created.getGender());
        assertEquals(athleteReq.identity(), created.getIdentity());
        assertEquals(athleteReq.height(), created.getHeight());
        assertEquals(athleteReq.weight(), created.getWeight());

        verify(athleteservice).createAthlete(athleteReq);
    }

    @Test
    void shouldFindByid() {
        AthleteResponse athleteResponse = new AthleteResponse(
            "1",
            "123.456.789-00",
            "Daniel Péricles do Nascimento",
            "dpericles6@gmail.com",
            45L,
            Gender.MALE,
            GenderIdentity.CISGENDER,
            182.5,
            105.5
        );

        when(athleteservice.getAthleteById("1"))
            .thenReturn(athleteResponse);

          AthleteResponse result = athleteController.findById("1");
    

            assertEquals("1", result.id());
            assertEquals("123.456.789-00", result.cpf());
            assertEquals("Daniel Péricles do Nascimento", result.name());
            assertEquals("dpericles6@gmail.com", result.email());
            assertEquals(45L, result.age());
            assertEquals(Gender.MALE, result.gender());
            assertEquals(GenderIdentity.CISGENDER, result.identity());
            assertEquals(182.5, result.height());
            assertEquals(105.5, result.weight());

            verify(athleteservice).getAthleteById("1");
    }

    @Test
    void shouldUpdateAthlete(){
        AthleteRequest athleteReq = new AthleteRequest(
            "123.456.789-00",
            "Daniel Péricles do Nascimento",
            "dpericles6@gmail.com",
            45L,
            Gender.MALE,
            GenderIdentity.CISGENDER,
            182.5,
            105.5
        );

        Athlete athlete = new Athlete();
        athlete.setCpf("123.456.789-00");
        athlete.setName("Daniel Péricles do Nascimento");
        athlete.setEmail("dpericles6@gmail.com");
        athlete.setAge(45L);
         athlete.setGender(Gender.MALE);
         athlete.setIdentity(GenderIdentity.CISGENDER);
        athlete.setHeight(182.5);
        athlete.setWeight(105.5);

        when(athleteservice.updateAthlete("1", athleteReq))
            .thenReturn(athlete);
        Athlete updated = athleteController.updateAthlete("1", athleteReq);
        

        assertNotNull(updated);
        assertEquals("123.456.789-00", athleteReq.cpf());
        assertEquals("Daniel Péricles do Nascimento", athleteReq.name());
        assertEquals("dpericles6@gmail.com", athleteReq.email());
        assertEquals(45L, athleteReq.age());
        assertEquals(Gender.MALE, athleteReq.gender());
        assertEquals(GenderIdentity.CISGENDER, athleteReq.identity());
        assertEquals(182.5, athleteReq.height());
        assertEquals(105.5, athleteReq.weight());
        
        verify(athleteservice).updateAthlete("1",athleteReq);

    }

    @Test
    void shouldPatchAtlhete(){

          AthleteRequest athleteReq = new AthleteRequest(
            "123.456.789-00",
            "Daniel Péricles do Nascimento",
            "dpericles6@gmail.com",
            45L,
            Gender.MALE,
            GenderIdentity.CISGENDER,
            107.5,
            107.5
        );
        Athlete athlete = new Athlete();
        athlete.setHeight(182.5);


        when(athleteservice.pathAthlete("1", athleteReq))
        .thenReturn(athlete);

    Athlete patch = athleteController.patchAthlete("1", athleteReq);


        assertNotNull(patch);
        assertEquals(182.5, athlete.getHeight());

    }

    @Test
    void shouldFindByCpf(){
         Athlete athlete = new Athlete();
        athlete.setId("1");
        athlete.setCpf("123.456.789-00");
        athlete.setName("Daniel Péricles do Nascimento");
        athlete.setEmail("dpericles6@gmail.com");
        athlete.setAge(45L);
        athlete.setGender(Gender.MALE);
        athlete.setIdentity(GenderIdentity.CISGENDER);
        athlete.setHeight(182.5);
        athlete.setWeight(105.5);

         when(athleteservice.findbyCpf(anyString()))
        .thenReturn(athlete);

        ResponseEntity<AthleteResponse> result =
            athleteController.findByCpf("123.456.789-00");

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());

        AthleteResponse body = result.getBody();
    
        assertNotNull(body);
        assertEquals("1", body.id());
        assertEquals("Daniel Péricles do Nascimento", body.name());
        assertEquals("123.456.789-00", body.cpf());
        assertEquals("dpericles6@gmail.com", body.email());
        assertEquals(45L, body.age());
        assertEquals(Gender.MALE, body.gender());
        assertEquals(GenderIdentity.CISGENDER, body.identity());
        assertEquals(182.5, body.height());
        assertEquals(105.5, body.weight());

        verify(athleteservice).findbyCpf("123.456.789-00");

    }



    @Test
    void shouldDeleteAthlete(){
          Athlete existAthlete = new Athlete();
            existAthlete.setId("1");
            existAthlete.setCpf("999.999.999-99");
            existAthlete.setName("Daniel Péricles do Nascimento");
            existAthlete.setAge(45L);
            existAthlete.setGender(Gender.MALE);
            existAthlete.setIdentity(GenderIdentity.CISGENDER);
            existAthlete.setEmail("dpericles6@gmail.com");
            existAthlete.setHeight(181.90);
            existAthlete.setWeight(105.10);
            existAthlete.setUserId("user-1");
    

          doNothing().when(athleteservice).deleteAthlete("1");
          
          athleteController.deleteAhtlete("1");

          verify(athleteservice).deleteAthlete("1");

    }
   
}
