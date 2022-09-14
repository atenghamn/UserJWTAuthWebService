package com.example.userjwtauthwebservice.user.dto;

public record CreateUser(
        String username,
        String password,
        Boolean isAdministrator
) {
}
