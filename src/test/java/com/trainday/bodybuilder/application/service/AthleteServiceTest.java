package com.trainday.bodybuilder.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import com.trainday.bodybuilder.api.DTO.request.LoginRequest;
import com.trainday.bodybuilder.api.DTO.request.RegisterRequest;
import com.trainday.bodybuilder.domain.model.enums.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mongodb.DuplicateKeyException;
import com.trainday.bodybuilder.api.DTO.request.AthleteRequest;
import com.trainday.bodybuilder.api.DTO.response.AthleteResponse;
import com.trainday.bodybuilder.domain.model.Athlete;
import com.trainday.bodybuilder.domain.model.Login;
import com.trainday.bodybuilder.domain.model.enums.Gender;
import com.trainday.bodybuilder.domain.model.enums.GenderIdentity;
import com.trainday.bodybuilder.domain.repository.AthleteRepository;
import com.trainday.bodybuilder.domain.repository.LoginRepository;


@ExtendWith(MockitoExtension.class)
public class AthleteServiceTest {

    @Mock
    AthleteRepository athleterepository;

    @Mock
    LoginRepository loginrepository;

    @Spy
    @InjectMocks
    AthleteService athleteservice;

    @Test
    void shouldcreateAhtlete(){
        RegisterRequest registerRequest = new RegisterRequest(
                "999.999.999-99",
                LocalDate.of(1980, 01, 01),
                "athlete@host.com",
                "******",
                Role.ATHLETE


        );
        AthleteRequest request = new AthleteRequest(
            "Maria Silva",
            null,
            Gender.FEMALE,
            GenderIdentity.CISGENDER,
            1.68,
            62.0

        );
        Login login = new Login();
        login.setCpf(registerRequest.cpf());

        when(athleterepository.save(any(Athlete.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));
        when(loginrepository.findByCpf(registerRequest.cpf()))
                .thenReturn(Optional.of(login));
        
        Athlete create = athleteservice.createAthlete(request, registerRequest.cpf());

        assertNotNull(create);

        assertEquals(request.name(), create.getName());
        assertEquals(request.socialName(), create.getSocialname());
        assertEquals(request.height(), create.getHeight());
        assertEquals(request.weight(), create.getWeight());
        assertEquals(login.getId(), create.getUserId());
    }

    @Test
    void shouldThrowConflictWhenCpfAlreadyExists(){
        RegisterRequest registerReq = new RegisterRequest(
                "999.999.999-99",
                LocalDate.of(1980, 01, 01),
                "athlete@host.com",
                "000000",
                Role.ATHLETE
        );
        AthleteRequest request = new AthleteRequest(

            "Maria Silva",
            null,
            Gender.FEMALE,
            GenderIdentity.CISGENDER,
            1.68,
            62.0

        );
        Login login = new Login();
        login.setCpf(registerReq.cpf());

        Athlete existingAthlete = new Athlete();
        existingAthlete.setCpf("999.999.999-99");


        when(loginrepository.findByCpf(registerReq.cpf()))
            .thenReturn(Optional.of(login));
        when(athleterepository.findByCpf(registerReq.cpf()))
            .thenReturn(Optional.of(existingAthlete));

        AthleteCpfAlreadyExistsException exception = assertThrows(
                AthleteCpfAlreadyExistsException.class,
                () -> athleteservice.createAthlete(request, registerReq.cpf())
        );

        assertEquals(   "CPF already exists: " + registerReq.cpf(),
                exception.getMessage());
        verify(athleterepository, never()).save(any(Athlete.class));
    }




    @Test 
        void shouldGetByCPF(){ 
              Athlete athlete = new Athlete(
            "1",
            "12345678900",
            "Maria Silva",
            null,
            "maria@email.com",
                      LocalDate.of(2000, 01, 01),
            Gender.FEMALE,
            GenderIdentity.CISGENDER,
            1.68,
            62.0,
            "user-1",
                      Role.ATHLETE
        );
        when(athleterepository.findByCpf("12345678900"))
            .thenReturn(Optional.of(athlete));

        AthleteResponse state = athleteservice.findMyProfile("12345678900");

        assertNotNull(state);
        assertEquals(athlete.getId(), state.id());
        assertEquals(athlete.getCpf(), state.cpf());
        assertEquals(athlete.getName(), state.name());
        assertEquals(athlete.getSocialname(), state.socialName());
        assertEquals(athlete.getEmail(), state.email());
        assertEquals(athlete.getBorn(), state.born());
        assertEquals(athlete.getGender(), state.gender());
        assertEquals(athlete.getIdentity(), state.identity());
        assertEquals(athlete.getHeight(), state.height());
        assertEquals(athlete.getWeight(), state.weight());
    }

    @Test
    void shouldUpdateAthlete(){
        Athlete existAthlete = new Athlete();

        existAthlete.setCpf("999.999.999-99");
        existAthlete.setName("Daniel Péricles do Nascimento");
        existAthlete.setSocialname(null);
        existAthlete.setBorn(LocalDate.of(2000, 01, 01));
        existAthlete.setEmail("dpericles6@gmail.com");
        existAthlete.setGender((Gender.FEMALE));
        existAthlete.setIdentity(GenderIdentity.CISGENDER);
        existAthlete.setHeight(181.90);
        existAthlete.setWeight(105.10);

        Athlete updateAthlete = new Athlete();
        updateAthlete.setCpf("999.999.999-99");
        updateAthlete.setName("Daniel Péricles do Nascimento");
        updateAthlete.setSocialname(null);
        updateAthlete.setBorn(LocalDate.of(2000, 01, 01));
        updateAthlete.setGender((Gender.MALE));
        updateAthlete.setIdentity(GenderIdentity.CISGENDER);
        updateAthlete.setEmail("dpericles6@hotmail.com");
        updateAthlete.setHeight(182.0);
        updateAthlete.setWeight(104.90);

        when(athleterepository.findById("1"))
            .thenReturn(Optional.of(existAthlete));

        when(athleterepository.save(any(Athlete.class)))
            .thenReturn(existAthlete);
    
        Athlete result = athleteservice.updateAthlete("1" , new AthleteRequest(
            "Daniel Péricles do Nascimento",
            null,
            Gender.MALE,
            GenderIdentity.CISGENDER,
            null,
            103.5

        ));

        assertNotNull(result);
        assertEquals("Daniel Péricles do Nascimento", result.getName());
        assertEquals(103.5, result.getWeight());
     
    }

    @Test
    void shouldPatchAthlete(){

          Athlete existAthlete = new Athlete();
            existAthlete.setCpf("999.999.999-99");
            existAthlete.setName("Daniel Péricles do Nascimento");
            existAthlete.setSocialname(null);
            existAthlete.setBorn(LocalDate.of(2000, 01, 01));
            existAthlete.setEmail("dpericles6@gmail.com");
            existAthlete.setGender((Gender.FEMALE));
            existAthlete.setIdentity(GenderIdentity.CISGENDER);
            existAthlete.setHeight(181.90);
            existAthlete.setWeight(181.90);
            existAthlete.setRole(Role.ATHLETE);
            Athlete patchAthlete = new Athlete();
            patchAthlete.setSocialname("Daniel Péricles");
            patchAthlete.setGender(Gender.MALE);
            patchAthlete.setWeight(107.5);

             when(athleterepository.findByCpf("999.999.999-99"))
            .thenReturn(Optional.of(existAthlete));

         when(athleterepository.save(any(Athlete.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

            Athlete result = athleteservice.pathAthlete("999.999.999-99" , new AthleteRequest(
                "Daniel Péricles do Nascimento",
                "Daniel Péricles",
                    Gender.MALE,
            GenderIdentity.CISGENDER,
            182.5,
                    107.5

            ));
            
           assertNotNull(result);
           assertEquals("Daniel Péricles do Nascimento", result.getName());

           assertEquals("Daniel Péricles", result.getSocialname());
           assertEquals(Gender.MALE, result.getGender());
           assertEquals(GenderIdentity.CISGENDER, result.getIdentity());
           assertEquals(182.5, result.getHeight());
           assertEquals(107.5, result.getWeight());


    }

    @Test
    void shouldUpdateAthlete_whenCpfIsProvided() {
        Athlete existAthlete = new Athlete();
        existAthlete.setId("1");
        existAthlete.setName("Maria");

        when(athleterepository.findById("1"))
            .thenReturn(Optional.of(existAthlete));

        when(athleterepository.save(any(Athlete.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Athlete result = athleteservice.updateAthlete(
                "1",
                new AthleteRequest(
                        "Maria Silva",
                        null,
                        null,
                        null,
                        null,
                        null
                )
        );
    }


    @Test
    void shouldThrowExceptionWhenCpfAlreadyExistsforUpdate() {

        Athlete existAthlete = new Athlete();
        existAthlete.setCpf("111.111.111-11");

           AthleteRequest request =  new AthleteRequest(

                null,
                null,
                null,
                null,
                null,
                null

            );

        when(athleterepository.findById(anyString()))
                .thenReturn(Optional.of(existAthlete));

        when(athleterepository.save(any(Athlete.class)))
                .thenThrow(DuplicateKeyException.class);

        assertThrows(
                AthleteCpfAlreadyExistsException.class,
                () -> athleteservice.updateAthlete("1", request)
        );
    }


    @Test
    void shoulddeleteAthlete(){

        Athlete existAthlete = new Athlete();
        existAthlete.setId("1");
        existAthlete.setCpf("999.999.999-99");
        existAthlete.setName("Daniel Péricles do Nascimento");
        existAthlete.setSocialname(null);
        existAthlete.setBorn(        LocalDate.of(2000, 01, 01));
        existAthlete.setGender(Gender.MALE);
        existAthlete.setIdentity(GenderIdentity.CISGENDER);
        existAthlete.setEmail("dpericles6@gmail.com");
        existAthlete.setHeight(181.90);
        existAthlete.setWeight(105.10);
        existAthlete.setUserId("user-1");

        when(athleterepository.findById("1"))
            .thenReturn(Optional.of(existAthlete));


        doNothing().when(athleterepository).deleteById("1");
        doNothing().when(loginrepository).deleteById("user-1");

        assertDoesNotThrow(() -> athleteservice.deleteAthlete("1"));

         verify(athleterepository).deleteById("1");
         verify(loginrepository).deleteById("user-1");

    }

    @Test
    void shouldThrowExceptionWhenCpfAlreadyExists() {

        Athlete athlete = new Athlete();
        athlete.setId("1");
        athlete.setCpf("999.999.999-99");

        when(athleterepository.findByCpf("999.999.999-99"))
                .thenReturn(Optional.of(athlete));

        assertThrows(
                AthleteCpfAlreadyExistsException.class,
                () -> athleteservice.validateCpfAvailable(
                        "999.999.999-99",
                        null
                )
        );
    }


}
