package com.example.userjwtauthwebservice.user.dto;

public record UserDetail(
        int id,
        String username,
        boolean isAdministrator

) {
}
