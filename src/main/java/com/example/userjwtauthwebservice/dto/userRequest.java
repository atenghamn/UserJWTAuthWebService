package com.example.userjwtauthwebservice.dto;

public record userRequest(
        String username,
        String password
) {
}
