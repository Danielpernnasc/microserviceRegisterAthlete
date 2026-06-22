package com.trainday.bodybuilder.infra.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import com.trainday.bodybuilder.domain.model.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.trainday.bodybuilder.infra.Service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtServiceTest {

  private JwtService jwtService;

    @BeforeEach
    void SetUp(){
        jwtService = new JwtService();

        ReflectionTestUtils.setField(jwtService, "secret", "chave-falsa-so-para-teste-unitario-2026");
        ReflectionTestUtils.setField(jwtService, "expiration", 3600000L);
        }
    

    @Test
    void shouldGenerateToken(){
        String idWait = "usuary-123";
         String token = jwtService.generateToken( idWait, "athlete@host.com", "999.999.999-99", "athlete1", Role.ATHLETE);

         assertNotNull(token);
         assertFalse(token.isBlank());

    }

    @Test
    void shouldContentCorret(){
        String idWait = "usuary-123";

        String token = jwtService.generateToken(idWait, "athlete@host.com", "999.999.999-99", "athlete1", Role.ATHLETE);


        Claims claims = Jwts.parserBuilder()
            .setSigningKey(jwtService.testgetKey())
            .build()
            .parseClaimsJws(token)
            .getBody();

            assertEquals("athlete@host.com", claims.getSubject());
    }

    @Test
    void shouldExpiredInFuture(){
        String idWait = "usuary-123";
        String token = jwtService.generateToken(idWait,"usuary-123", "user1", "athlete1", Role.ATHLETE);

        Claims claims = Jwts.parserBuilder()
                    .setSigningKey(jwtService.testgetKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
                    
        assertTrue(claims.getExpiration().after(new Date()));

    }
    

    @Test
    void shouldGererTokenValid(){
        String idWait = "usuary-123";
        String token = jwtService.generateToken(idWait, "usuary-123", "user1", "athlete1", Role.ATHLETE);


        assertDoesNotThrow(() -> 
                Jwts.parserBuilder()
                    .setSigningKey(jwtService.testgetKey())
                    .build()
                    .parseClaimsJws(token));
    }

    @Test
    void shoudextractEmail(){

        String idWait = "usuary-123";
        String emailWait = "daniel@host.com";
        String token = jwtService.generateToken(idWait, emailWait, "user1", "athlete1", Role.ATHLETE);
        String emailExtraido = jwtService.extractSubject(token);
        assertEquals(emailWait, emailExtraido);
    }

    @Test
    void shouldthrowsExceptionWithTokenValid(){
        String tokeGarbage = "this.is.not.a.token";

        assertThrows(Exception.class, () -> jwtService.extractSubject(tokeGarbage));
    }

    @Test
    void shouldTokenValid(){
        String idWait = "usuary-123";
        String token = jwtService.generateToken(idWait,"daniel@host.com", "user1", "athlete1", Role.ATHLETE);

        boolean result = jwtService.isTokenValid(token);

        assertTrue(result);
    }

    @Test
    void shouldReturnFalseForTokenInvalid(){
        String tokeninvalid = "this.is.not.a.token.valid";

        boolean result = jwtService.isTokenValid(tokeninvalid);

        assertFalse(result);
    }

    @Test
    void shouldReturnFalseToTokenExpired(){
        ReflectionTestUtils.setField(jwtService, "expiration", -1L);
        String idWait = "usuary-123";
        String token = jwtService.generateToken(idWait, "daniel@host.com", "user1", "athlete1", Role.ATHLETE);

        boolean result = jwtService.isTokenValid(token);

        assertFalse(result);
    }

    @Test
    void shoulExtractUserName(){
        String idWait = "usuary-123";
        String userNameWait = "daniel@host.com";
        String token = jwtService.generateToken(idWait, userNameWait, "user1", "athlete1", Role.ATHLETE);
        String userNameExtracted = jwtService.extractUsername(token);
        assertEquals(userNameWait, userNameExtracted);
    }

   


}


