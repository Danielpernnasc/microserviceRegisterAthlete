package com.trainday.bodybuilder.infra.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.trainday.bodybuilder.domain.model.Login;
import com.trainday.bodybuilder.domain.repository.LoginRepository;
import com.trainday.bodybuilder.infra.Service.LoginUserDetailsService;

@ExtendWith(MockitoExtension.class)
public class LoginUserDetailsServiceTest {

    @Mock
    LoginRepository loginRepository;

    @InjectMocks
    LoginUserDetailsService loginUserDetailsService;

    @Test
    void shouldLoadByUserName() {
        Login login = new Login();
        login.setId("1");
        login.setEmail("daniel@email.com");
        login.setPassword("123456");

        when(loginRepository.findByEmail("daniel@email.com"))
                .thenReturn(Optional.of(login));

        UserDetails userDetails =
                loginUserDetailsService.loadUserByUsername("daniel@email.com");

        assertEquals("daniel@email.com", userDetails.getUsername());
        assertEquals("123456", userDetails.getPassword());
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        when(loginRepository.findByEmail("naoexiste@email.com"))
            .thenReturn(Optional.empty());

        assertThrows(
             UsernameNotFoundException.class,
             () -> loginUserDetailsService.loadUserByUsername("naoexiste@email.com")
            );
    }



}
