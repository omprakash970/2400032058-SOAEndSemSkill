package com.example.jwtvalidator.dto;

import java.util.List;

public record JwtResponse(
        String username,
        List<String> roles
) {
}