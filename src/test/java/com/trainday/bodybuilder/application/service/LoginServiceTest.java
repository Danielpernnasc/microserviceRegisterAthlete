package com.trainday.bodybuilder.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import com.trainday.bodybuilder.api.DTO.request.RegisterRequest;
import com.trainday.bodybuilder.domain.model.enums.Role;
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
import com.trainday.bodybuilder.infra.Service.JwtService;

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
        RegisterRequest request = new RegisterRequest(

            "999.999.999-99",
                LocalDate.of(2000, 1,1),
            "athlete@host.com",
                "123456",
                Role.ATHLETE
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
        assertEquals("athlete@host.com", service.email());
        assertEquals(Role.ATHLETE, service.role());
        verify(passwordEncoder).encode("123456");
        verify(loginrepository).save(any(Login.class));

    }

  @Test
    void shoudauthenticate() {

        Login user = new Login();
        user.setId("user123");
        user.setEmail("athlete@host.com");
        user.setCpf("999.999.999-99");
        user.setPassword("123456");
        user.setRole(Role.ATHLETE);

        Athlete athlete = new Athlete();
        athlete.setId("athlete123");
        athlete.setUserId("user123");

        when(loginrepository.findByEmail("athlete@host.com"))
            .thenReturn(Optional.of(user));

        when(athleterepository.findByUserId("user123"))
            .thenReturn(Optional.of(athlete));

      user.setPassword("senhaCriptografada");

      when(passwordEncoder.matches(
              "123456",
              "senhaCriptografada"))
              .thenReturn(true);

        when(jwtservice.generateToken(
                        anyString(),
                        anyString(),
                        anyString(),
                        anyString(),
                        any(Role.class)))
            .thenReturn("token_fake");

        String token = loginservice.authenticate(
            new LoginRequest( "athlete@host.com", "123456")
        );

        assertEquals("token_fake", token);
}

}
