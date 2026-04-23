package com.trainday.bodybuilder.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.trainday.bodybuilder.api.DTO.request.AthleteRequest;
import com.trainday.bodybuilder.api.DTO.response.AthleteResponse;
import com.trainday.bodybuilder.domain.model.Athlete;
import com.trainday.bodybuilder.domain.model.Login;
import com.trainday.bodybuilder.domain.repository.AthleteRepository;
import com.trainday.bodybuilder.domain.repository.LoginRepository;


@ExtendWith(MockitoExtension.class)
public class AthleteServiceTest {

    @Mock
    AthleteRepository athleterepository;

    @Mock
    LoginRepository loginrepository;

    @InjectMocks
    AthleteService athleteservice;

    @Test
    void shouldcreateAhtlete(){
        AthleteRequest request = new AthleteRequest(
            "12345678900",
            "Maria Silva",
            "maria@email.com",
            25L,
            1.68,
            62.0,
            18L
        );
        Login login = new Login();
        login.setId("user-1");
        login.setEmail(request.email());

        when(loginrepository.findByEmail(request.email()))
            .thenReturn(Optional.of(login));
        when(athleterepository.save(any(Athlete.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));
        
        Athlete create = athleteservice.createAthlete(request);

        assertNotNull(create);
        assertEquals(request.cpf(), create.getCPF());
        assertEquals(request.name(), create.getName());
        assertEquals(request.email(), create.getEmail());
        assertEquals(request.age(), create.getAge());
        assertEquals(request.height(), create.getHeight());
        assertEquals(request.weight(), create.getWeight());
        assertEquals(request.percentagefat(), create.getpercentageFat());
        assertEquals(login.getId(), create.getUserId());
    }

    @Test
    void shouldgetAthleteById(){
          Athlete athlete = new Athlete(
            "1",
            "12345678900",
            "Maria Silva",
            "maria@email.com",
            25L,
            1.68,
            62.0,
            18L,
            "user-1"
        );

    when(athleterepository.findById("1"))
            .thenReturn(Optional.of(athlete));
    
    
      AthleteResponse state = athleteservice.getAthleteById("1");

        assertNotNull(state);
        assertEquals(athlete.getId(), state.id());
        assertEquals(athlete.getCPF(), state.cpf());
        assertEquals(athlete.getName(), state.name());
        assertEquals(athlete.getEmail(), state.email());
        assertEquals(athlete.getAge(), state.age());
        assertEquals(athlete.getHeight(), state.height());
        assertEquals(athlete.getWeight(), state.weight());
        assertEquals(athlete.getpercentageFat(), state.percentagefat());

    }

    @Test
    void shouldUpdateAthlete(){
        Athlete existAthlete = new Athlete();

        existAthlete.setCPF("999.999.999-99");
        existAthlete.setName("Daniel Péricles do Nascimento");
        existAthlete.setAge(45L);
        existAthlete.setEmail("dpericles6@gmail.com");
        existAthlete.setHeight(181.90);
        existAthlete.setWeight(105.10);
        existAthlete.setpercentageFat(15L);

        Athlete updateAthlete = new Athlete();
        updateAthlete.setCPF("999.999.999-99");
        updateAthlete.setName("Daniel Péricles do Nascimento");
        updateAthlete.setAge(44L);
        updateAthlete.setEmail("dpericles6@hotmail.com");
        updateAthlete.setHeight(182.0);
        updateAthlete.setWeight(104.90);
        updateAthlete.setpercentageFat(15L);

        when(athleterepository.findById("1"))
            .thenReturn(Optional.of(existAthlete));

        when(athleterepository.save(any(Athlete.class)))
            .thenReturn(existAthlete);
    
        Athlete result = athleteservice.updateAthlete("1" , new AthleteRequest(
            null,
             null,    
            "Daniel Péricles do Nascimento",
            null,
            null,
            103.5,
            null

        ));

        assertNotNull(result);
        assertEquals("Daniel Péricles do Nascimento", result.getName());
        assertEquals(103.5, result.getWeight());
     
    }



    @Test
    void shoulddeleteAthlete(){

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

        when(athleterepository.findById("1"))
            .thenReturn(Optional.of(existAthlete));


        doNothing().when(athleterepository).deleteById("1");
        doNothing().when(loginrepository).deleteById("user-1");

        assertDoesNotThrow(() -> athleteservice.deleteAthlete("1"));

         verify(athleterepository).deleteById("1");
         verify(loginrepository).deleteById("user-1");

            

        

    }


}
