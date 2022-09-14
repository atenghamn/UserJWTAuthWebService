package com.example.userjwtauthwebservice.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;

public record SimpleResponse(
        Instant created,
        @JsonInclude(JsonInclude.Include.NON_NULL) String message)
 {
}
