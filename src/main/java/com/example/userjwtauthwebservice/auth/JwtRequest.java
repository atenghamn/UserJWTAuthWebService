package com.example.userjwtauthwebservice.auth;

public record JwtRequest(
        String username,
        String password
) {
}
