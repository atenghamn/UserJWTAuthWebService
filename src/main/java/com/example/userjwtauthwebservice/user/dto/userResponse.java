package com.example.userjwtauthwebservice.user.dto;

public record userResponse(
        int id,
        String username,
        boolean isAdminstrator
) {
}
