package com.trainday.bodybuilder.infra.Service;

import com.trainday.bodybuilder.domain.model.enums.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService{

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;


    private Key getKey(){
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    
    public Key testgetKey(){
        return getKey();
    }

    public String generateToken(
            String email,
            String cpf,
            String userId,
            String athleteId,
            Role role
    ) {


        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("athleteId", athleteId);
        claims.put("role", role.name());



        return Jwts.builder()
                .setClaims(claims)
                .setSubject(cpf)
                .setIssuedAt(new Date())
                .setExpiration(
                    new Date(System.currentTimeMillis() + expiration)
                )
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    public String extractSubject(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenValid(String token){
        try{
            Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token);
            return true;
        }catch (JwtException e){
            e.printStackTrace();
            return false;
        }
    }

    public String extractUsername(String token) {
          return Jwts.parserBuilder()
                   .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
    }



}
