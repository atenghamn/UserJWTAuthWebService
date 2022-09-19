package com.example.userjwtauthwebservice.auth.dto;

public record JwtRequest(
        String username,
        String password
) {
}
