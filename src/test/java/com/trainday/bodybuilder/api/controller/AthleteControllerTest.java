package com.trainday.bodybuilder.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import com.trainday.bodybuilder.domain.model.enums.Role;
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
import com.trainday.bodybuilder.infra.Service.JwtService;

import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
public class AthleteControllerTest {

    @Mock
    AthleteService athleteservice;

    @Mock
    AthleteRepository athleteRepository;

    @InjectMocks
    AthleteController athleteController;

    @Mock
    JwtService jwtService;

    @Test
    void shouldSaveAhtlete() {
        AthleteRequest athleteReq = new AthleteRequest(
            "Daniel Péricles do Nascimento",
            null,
            Gender.MALE,
            GenderIdentity.CISGENDER,
            182.5,
            105.5
        );

        Athlete athlete = new Athlete();
        athlete.setId("user-1");
        athlete.setCpf("123.456.789-00");
        athlete.setName("Daniel Péricles do Nascimento");
        athlete.setEmail("dpericles6@gmail.com");
        athlete.setBorn(LocalDate.of(2000, 1, 1));
        athlete.setGender(Gender.MALE);
        athlete.setIdentity(GenderIdentity.CISGENDER);
        athlete.setHeight(182.5);
        athlete.setWeight(105.5);


        Authentication  authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("user-1");
     
        when(athleteservice.createAthlete(athleteReq, "user-1"))
            .thenReturn(athlete);



        ResponseEntity<Athlete> created = athleteController.save(athleteReq, authentication);

        assertNotNull(created);
        assertEquals(athleteReq.name(), created.getBody().getName());
        assertEquals(athleteReq.socialName(), created.getBody().getSocialname());
        assertEquals(athleteReq.gender(), created.getBody().getGender());
        assertEquals(athleteReq.identity(), created.getBody().getIdentity());
        assertEquals(athleteReq.height(), created.getBody().getHeight());
        assertEquals(athleteReq.weight(), created.getBody().getWeight());


        verify(athleteservice).createAthlete(athleteReq, "user-1");
    }


    @Test
    void shouldUpdateAthlete(){
        AthleteRequest athleteReq = new AthleteRequest(
            "Daniel Péricles do Nascimento",
            null,
            Gender.MALE,
            GenderIdentity.CISGENDER,
            182.5,
            105.5
        );

        Athlete athlete = new Athlete();
        athlete.setCpf("123.456.789-00");
        athlete.setName("Daniel Péricles do Nascimento");
        athlete.setSocialname(null);
        athlete.setEmail("dpericles6@gmail.com");
        athlete.setBorn(      LocalDate.of(2000, 1, 1));
         athlete.setGender(Gender.MALE);
         athlete.setIdentity(GenderIdentity.CISGENDER);
        athlete.setHeight(182.5);
        athlete.setWeight(105.5);

        Authentication  authentication = mock(Authentication.class);

        when(authentication.getName()).thenReturn("user-1");

        when(athleteservice.updateAthlete("user-1", athleteReq))
            .thenReturn(athlete);
        Athlete updated = athleteController.updateAthlete(athleteReq, authentication);
        

        assertNotNull(updated);
        assertEquals("Daniel Péricles do Nascimento", athleteReq.name());
        assertEquals(Gender.MALE, athleteReq.gender());
        assertEquals(GenderIdentity.CISGENDER, athleteReq.identity());
        assertEquals(182.5, athleteReq.height());
        assertEquals(105.5, athleteReq.weight());
        
        verify(athleteservice).updateAthlete("user-1" ,athleteReq);

    }

    @Test
    void shouldPatchAtlhete(){

          AthleteRequest athleteReq = new AthleteRequest(
            "Daniel Péricles do Nascimento",
            null,
            Gender.MALE,
            GenderIdentity.CISGENDER,
            107.5,
            107.5
        );
        Athlete athlete = new Athlete();
        athlete.setHeight(182.5);


        Authentication  authentication = mock(Authentication.class);

        when(authentication.getName()).thenReturn("user-1");
        when(athleteservice.pathAthlete("user-1", athleteReq))
        .thenReturn(athlete);

        Athlete patched = athleteController.patchAthlete(athleteReq, authentication);

        assertEquals(182.5, patched.getHeight());



    }

    @Test
    void shouldFindByCpf(){
         AthleteResponse athlete = new AthleteResponse(
                 "123456789",
                 "123.456.789-00",
                 "Daniel Péricles do Nascimento",
                 null,
                 "dpericles6@gmail.com",
                 LocalDate.of(2000, 1, 1),
                 Gender.MALE,
                 GenderIdentity.CISGENDER,
                 182.5,
                 105.5,
                 Role.ATHLETE
         );

        Authentication  authentication = mock(Authentication.class);

        when(authentication.getName()).thenReturn("user-1");


        when(athleteservice.findMyProfile("user-1"))
                .thenReturn(athlete);


        ResponseEntity<AthleteResponse> result =
            athleteController.findByCpf(authentication);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());

        AthleteResponse body = result.getBody();
    
        assertNotNull(body);
        assertEquals("123456789", body.id());
        assertEquals("Daniel Péricles do Nascimento", body.name());
        assertEquals("123.456.789-00", body.cpf());
        assertEquals("dpericles6@gmail.com", body.email());
        assertEquals(LocalDate.of(2000, 01, 01), body.born());
        assertEquals(Gender.MALE, body.gender());
        assertEquals(GenderIdentity.CISGENDER, body.identity());
        assertEquals(182.5, body.height());
        assertEquals(105.5, body.weight());


        verify(athleteservice).findMyProfile("user-1");

    }



    @Test
    void shouldDeleteAthlete(){
          Athlete existAthlete = new Athlete();
            existAthlete.setId("1");
            existAthlete.setCpf("999.999.999-99");
            existAthlete.setName("Daniel Péricles do Nascimento");
            existAthlete.setBorn(LocalDate.of(2000, 01, 01));
            existAthlete.setGender(Gender.MALE);
            existAthlete.setIdentity(GenderIdentity.CISGENDER);
            existAthlete.setEmail("dpericles6@gmail.com");
            existAthlete.setHeight(181.90);
            existAthlete.setWeight(105.10);
            existAthlete.setRole(Role.ATHLETE);



        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("user-1");

        doNothing().when(athleteservice).deleteAthlete("user-1");
        athleteController.deleteAhtlete(authentication);


        verify(athleteservice).deleteAthlete("user-1");

    }
   
}
