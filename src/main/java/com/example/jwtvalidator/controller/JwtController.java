package com.example.jwtvalidator.controller;

import com.example.jwtvalidator.dto.JwtResponse;
import com.example.jwtvalidator.dto.TokenRequest;
import com.example.jwtvalidator.service.JwtService;

import io.jsonwebtoken.JwtException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/jwt")
public class JwtController {

    private final JwtService jwtService;

    public JwtController(JwtService jwtService) {
        this.jwtService = jwtService;
    }


    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(
            @RequestBody TokenRequest request) {

        try {
            if (request.token() == null ||
                    request.token().isBlank()) {

                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Token is required"));
            }

            JwtResponse response =
                    jwtService.validateToken(request.token());

            return ResponseEntity.ok(response);

        } catch (JwtException | IllegalArgumentException e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                            "error",
                            "Invalid or expired JWT token"
                    ));
        }
    }
}