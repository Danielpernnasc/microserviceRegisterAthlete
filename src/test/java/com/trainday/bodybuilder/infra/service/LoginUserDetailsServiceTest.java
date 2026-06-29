package com.trainday.bodybuilder.infra.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.trainday.bodybuilder.domain.model.enums.Role;
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
        login.setCpf("999.999.999-99");
        login.setEmail("daniel@email.com");
        login.setPassword("123456");
        login.setRole(Role.ATHLETE);

        when(loginRepository.findByCpf("999.999.999-99"))
                .thenReturn(Optional.of(login));

        UserDetails userDetails =
                loginUserDetailsService.loadUserByUsername("999.999.999-99");


        assertEquals("999.999.999-99", userDetails.getUsername());
        assertEquals("123456", userDetails.getPassword());
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        when(loginRepository.findByCpf("999.999.999-99"))
            .thenReturn(Optional.empty());

        assertThrows(
             UsernameNotFoundException.class,
             () -> loginUserDetailsService.loadUserByUsername("999.999.999-99")
            );
    }



}
