package com.trainday.bodybuilder.infra.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

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

         String token = jwtService.generateToken("usuary-123", "user1", "athlete1");

         assertNotNull(token);
         assertFalse(token.isBlank());

    }

    @Test
    void shouldContentCorret(){
        String idWait = "usuary-123";

        String token = jwtService.generateToken(idWait, "user1", "athlete1");


        Claims claims = Jwts.parserBuilder()
            .setSigningKey(jwtService.testgetKey())
            .build()
            .parseClaimsJws(token)
            .getBody();

            assertEquals(idWait, claims.getSubject());
    }

    @Test
    void shouldExpiredInFuture(){
        String token = jwtService.generateToken("usuary-123", "user1", "athlete1");

        Claims claims = Jwts.parserBuilder()
                    .setSigningKey(jwtService.testgetKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
                    
        assertTrue(claims.getExpiration().after(new Date()));

    }
    

    @Test
    void shouldGererTokenValid(){
        String token = jwtService.generateToken("usuary-123", "user1", "athlete1");


        assertDoesNotThrow(() -> 
                Jwts.parserBuilder()
                    .setSigningKey(jwtService.testgetKey())
                    .build()
                    .parseClaimsJws(token));
    }

    @Test
    void shoudextractEmail(){

        String emailWait = "daniel@host.com";
        String token = jwtService.generateToken(emailWait, "user1", "athlete1");
        String emailExtraido = jwtService.extractEmail(token);
        assertEquals(emailWait, emailExtraido);
    }

    @Test
    void shouldthrowsExceptionWithTokenValid(){
        String tokeGarbage = "this.is.not.a.token";

        assertThrows(Exception.class, () -> jwtService.extractEmail(tokeGarbage));
    }

    @Test
    void shouldTokenValid(){
        String token = jwtService.generateToken("daniel@host.com", "user1", "athlete1");

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
        String token = jwtService.generateToken("daniel@host.com", "user1", "athlete1");

        boolean result = jwtService.isTokenValid(token);

        assertFalse(result);
    }

    @Test
    void shoulExtractUserName(){
        String userNameWait = "daniel@host.com";
        String token = jwtService.generateToken(userNameWait, "user1", "athlete1");
        String userNameExtracted = jwtService.extractUsername(token);
        assertEquals(userNameWait, userNameExtracted);
    }

   


}


