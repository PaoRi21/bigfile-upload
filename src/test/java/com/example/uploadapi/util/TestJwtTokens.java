package com.example.uploadapi.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.List;

public class TestJwtTokens {

    private static final String SECRET = "TuClaveSuperSecreta1234567890123456";

    public static String validToken() {
        return Jwts.builder()
                .setSubject("prueba")
                .claim("role", "ADMIN")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hora
                .signWith(SignatureAlgorithm.HS256, SECRET.getBytes())
                .compact();
    }

    public static String invalidToken() {
        return Jwts.builder()
                .setSubject("pruebaWrong")
                .claim("role", "WRONG")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600_000)) // 1 hora
                .signWith(SignatureAlgorithm.HS256, SECRET.getBytes())
                .compact();
    }
}
