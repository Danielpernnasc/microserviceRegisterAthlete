package com.trainday.bodybuilder.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

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
        AthleteRequest request = new AthleteRequest(
            "12345678900",
            "Maria Silva",
            "maria@email.com",
            25L,
            Gender.FEMALE,
            GenderIdentity.CISGENDER,
            1.68,
            62.0
        );
        Login login = new Login();
        login.setId("user-1");
        login.setEmail(request.email());

        when(loginrepository.findByEmail(request.email()))
            .thenReturn(Optional.of(login));
        when(athleterepository.save(any(Athlete.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));
        
        Athlete create = athleteservice.createAthlete(request, "user-1");

        assertNotNull(create);
        assertEquals(request.cpf(), create.getCpf());
        assertEquals(request.name(), create.getName());
        assertEquals(request.email(), create.getEmail());
        assertEquals(request.age(), create.getAge());
        assertEquals(request.height(), create.getHeight());
        assertEquals(request.weight(), create.getWeight());
        assertEquals(login.getId(), create.getUserId());
    }

    @Test
    void shouldThrowConflictWhenCpfAlreadyExists(){
        AthleteRequest request = new AthleteRequest(
            "12345678900",
            "Maria Silva",
            "maria@email.com",
            25L,
            Gender.FEMALE,
            GenderIdentity.CISGENDER,
            1.68,
            62.0
        );
        Login login = new Login();
        login.setId("user-1");
        login.setEmail(request.email());
        Athlete existingAthlete = new Athlete();
        existingAthlete.setId("athlete-1");
        existingAthlete.setCpf(request.cpf());

        when(loginrepository.findByEmail(request.email()))
            .thenReturn(Optional.of(login));
        when(athleterepository.findByCpf(request.cpf()))
            .thenReturn(Optional.of(existingAthlete));

        AthleteCpfAlreadyExistsException exception = assertThrows(
            AthleteCpfAlreadyExistsException.class,
            () -> athleteservice.createAthlete(request, "athlete-1")
        );

        assertEquals("CPF already exists: " + request.cpf(), exception.getMessage());
        verify(athleterepository, never()).save(any(Athlete.class));
    }

    @Test
    void shouldThrowExceptionWhenCpfAlreadyExists() {

           AthleteRequest request = new AthleteRequest(
            "12345678900",
            "Maria Silva",
            "maria@email.com",
            25L,
            Gender.FEMALE,
            GenderIdentity.CISGENDER,
            1.68,
            62.0
        );

        Login login = new Login();
        login.setId("user-1");
        login.setEmail(request.email());



        when(loginrepository.findByEmail(anyString()))
                .thenReturn(Optional.of(login));

        when(athleterepository.save(any(Athlete.class)))
                        .thenThrow(DuplicateKeyException.class);

        assertThrows(
                AthleteCpfAlreadyExistsException.class,
                () -> athleteservice.createAthlete(request, "user-1")
        );
    }

    @Test
    void shouldgetAthleteById(){
          Athlete athlete = new Athlete(
            "1",
            "12345678900",
            "Maria Silva",
            "maria@email.com",
            25L,
            Gender.FEMALE,
            GenderIdentity.CISGENDER,
            1.68,
            62.0,
            "user-1"
        );

    when(athleterepository.findById("1"))
            .thenReturn(Optional.of(athlete));
    
    
      AthleteResponse state = athleteservice.getAthleteById("1");

        assertNotNull(state);
        assertEquals(athlete.getId(), state.id());
        assertEquals(athlete.getCpf(), state.cpf());
        assertEquals(athlete.getName(), state.name());
        assertEquals(athlete.getEmail(), state.email());
        assertEquals(athlete.getAge(), state.age());
        assertEquals(athlete.getGender(), state.gender());
        assertEquals(athlete.getIdentity(), state.identity());
        assertEquals(athlete.getHeight(), state.height());
        assertEquals(athlete.getWeight(), state.weight());
    }

    @Test 
        void shouldGetByCPF(){ 
              Athlete athlete = new Athlete(
            "1",
            "12345678900",
            "Maria Silva",
            "maria@email.com",
            25L,
            Gender.FEMALE,
            GenderIdentity.CISGENDER,
            1.68,
            62.0,
            "user-1"
        );
        when(athleterepository.findByCpf("12345678900"))
            .thenReturn(Optional.of(athlete));

        Athlete state = athleteservice.findbyCpf("12345678900");

        assertNotNull(state);
        assertEquals(athlete.getId(), state.getId());
        assertEquals(athlete.getCpf(), state.getCpf());
        assertEquals(athlete.getName(), state.getName());
        assertEquals(athlete.getEmail(), state.getEmail());
        assertEquals(athlete.getAge(), state.getAge());
        assertEquals(athlete.getGender(), state.getGender());
        assertEquals(athlete.getIdentity(), state.getIdentity());
        assertEquals(athlete.getHeight(), state.getHeight());
        assertEquals(athlete.getWeight(), state.getWeight());  
    }

    @Test
    void shouldUpdateAthlete(){
        Athlete existAthlete = new Athlete();

        existAthlete.setCpf("999.999.999-99");
        existAthlete.setName("Daniel Péricles do Nascimento");
        existAthlete.setAge(45L);
        existAthlete.setEmail("dpericles6@gmail.com");
        existAthlete.setGender((Gender.FEMALE));
        existAthlete.setIdentity(GenderIdentity.CISGENDER);
        existAthlete.setHeight(181.90);
        existAthlete.setWeight(105.10);

        Athlete updateAthlete = new Athlete();
        updateAthlete.setCpf("999.999.999-99");
        updateAthlete.setName("Daniel Péricles do Nascimento");
        updateAthlete.setAge(44L);
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
            null,
             null,    
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
            existAthlete.setAge(45L);
            existAthlete.setEmail("dpericles6@gmail.com");
            existAthlete.setGender((Gender.FEMALE));
            existAthlete.setIdentity(GenderIdentity.CISGENDER);
            existAthlete.setHeight(181.90);
            existAthlete.setWeight(181.90);

            Athlete patchAthlete = new Athlete();
            patchAthlete.setGender(Gender.MALE);
            patchAthlete.setWeight(107.5);

             when(athleterepository.findById("1"))
            .thenReturn(Optional.of(existAthlete));

            when(athleterepository.save(any(Athlete.class)))
            .thenReturn(existAthlete);

            Athlete result = athleteservice.pathAthlete("1" , new AthleteRequest(
                "999.999.999-99",
                "Daniel Péricles do Nascimento",
                "dpericles6@gmail.com",
                45L,
                Gender.MALE,
                GenderIdentity.CISGENDER,
                181.90,
                107.50
            ));
            
           assertNotNull(result);
           assertEquals("999.999.999-99", result.getCpf());
           assertEquals("Daniel Péricles do Nascimento", result.getName());
           assertEquals("dpericles6@gmail.com", result.getEmail());
           assertEquals(45L, result.getAge());
           assertEquals(Gender.MALE, result.getGender());
           assertEquals(GenderIdentity.CISGENDER, result.getIdentity());
           assertEquals(181.90, result.getHeight());
           assertEquals(107.50, result.getWeight());

    }

    @Test
    void shouldUpdateAthlete_whenCpfIsProvided() {
        Athlete existAthlete = new Athlete();
        existAthlete.setCpf("111.111.111-11");

        when(athleterepository.findById("1"))
            .thenReturn(Optional.of(existAthlete));

        when(athleterepository.save(any(Athlete.class)))
            .thenReturn(existAthlete);

        // 👇 aqui você precisa MOCKAR o validateCpfAvailable se ele for interno
        doNothing().when(athleteservice).validateCpfAvailable("999.999.999-99", "1");

        Athlete result = athleteservice.updateAthlete(
            "1",
            new AthleteRequest(
                "999.999.999-99", // 👈 CPF agora vem preenchido
                null,
                null,
                null,
                null,
                null,
                null,
                null
            )
        );

        assertEquals("999.999.999-99", result.getCpf());
        

        verify(athleteservice).validateCpfAvailable("999.999.999-99", "1");
    }


    @Test
    void shouldThrowExceptionWhenCpfAlreadyExistsforUpdate() {

        Athlete existAthlete = new Athlete();
        existAthlete.setCpf("111.111.111-11");

           AthleteRequest request =  new AthleteRequest(
                "999.999.999-99", // 👈 CPF agora vem preenchido
                null,
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
        existAthlete.setAge(45L);
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


}
