package com.example.jwtvalidator.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.example.jwtvalidator.dto.JwtResponse;


import javax.crypto.SecretKey;
import java.util.List;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    public JwtResponse validateToken(String token) {

        SecretKey key = Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(secret)
        );

        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        String username = claims.getSubject();

        Object roleClaim = claims.get("roles");

        List<String> roles = roleClaim instanceof List<?> list
                ? list.stream().map(String::valueOf).toList()
                : List.of();

        return new JwtResponse(username, roles);
    }
}