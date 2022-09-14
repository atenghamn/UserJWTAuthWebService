package com.example.userjwtauthwebservice.post.dto;

public record PostDetail(
        int id,
        int userId,
        String title,
        String body
) { }
