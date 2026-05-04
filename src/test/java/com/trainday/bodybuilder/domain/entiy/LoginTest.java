package com.trainday.bodybuilder.domain.entiy;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.trainday.bodybuilder.domain.model.Login;

public class LoginTest {

    @Test
    void shouldCreateLogin(){
        Login login = new Login(
            "dpericles6@gmail.com",
            "123456"
        );

        assertEquals(null, login.getId());
        assertEquals("dpericles6@gmail.com", login.getEmail());
        assertEquals("123456", login.getPassword());
    }

    @Test
    void shouldLoginData(){
        Login login = new Login();
        login.setId("2");
        login.setEmail("mcristinaamorims@gmail.com");
        login.setPassword("456123");

        assertEquals("2", login.getId());
        assertEquals("mcristinaamorims@gmail.com", login.getEmail());
        assertEquals("456123", login.getPassword());
    }

    



}
