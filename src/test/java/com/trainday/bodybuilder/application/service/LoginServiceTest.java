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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.trainday.bodybuilder.api.DTO.request.LoginRequest;
import com.trainday.bodybuilder.api.DTO.response.LoginResponse;
import com.trainday.bodybuilder.domain.model.Athlete;
import com.trainday.bodybuilder.domain.model.Login;
import com.trainday.bodybuilder.domain.repository.AthleteRepository;
import com.trainday.bodybuilder.domain.repository.LoginRepository;
import com.trainday.bodybuilder.infra.security.JwtService;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {

    @Mock
    LoginRepository loginrepository;

    @Mock
    AthleteRepository athleterepository;

    @Mock
    JwtService jwtservice;

    @Mock
    AuthenticationManager authenticationManager;
    
    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    LoginService loginservice;


    @Test
    void shouldCreateLogin(){
        LoginRequest request = new LoginRequest(
            "dpericles6@gmail.com",
            "123456"
        );

        when(passwordEncoder.encode("123456")).thenReturn("senha-criptografada");
        when(loginrepository.save(any(Login.class)))
            .thenAnswer(invocation -> {
                Login savedLogin = invocation.getArgument(0);
                savedLogin.setId("id-user");
                return savedLogin;
            });

        LoginResponse service = loginservice.createLogin(request);

        assertNotNull(service);
        assertEquals("id-user", service.id());
        assertEquals("dpericles6@gmail.com", service.email());

        verify(passwordEncoder).encode("123456");
        verify(loginrepository).save(any(Login.class));

    }

  @Test
    void shoudauthenticate() {

        Login user = new Login();
        user.setId("user123");
        user.setEmail("teste@gmail.com");
        user.setPassword("123");

        Athlete athlete = new Athlete();
        athlete.setId("athlete123");
        athlete.setUserId("user123");

        when(loginrepository.findByEmail("teste@gmail.com"))
            .thenReturn(Optional.of(user));

        when(athleterepository.findByUserId("user123"))
            .thenReturn(Optional.of(athlete));

        when(jwtservice.generateToken(
                "teste@gmail.com",
                "user123",
                "athlete123"))
            .thenReturn("token_fake");

        String token = loginservice.authenticate(
            new LoginRequest( "teste@gmail.com", "123")
        );

        assertEquals("token_fake", token);
}

}
