package com.example.userjwtauthwebservice.dto;

public record JwtRequest(
        String username,
        String password
) {
}
