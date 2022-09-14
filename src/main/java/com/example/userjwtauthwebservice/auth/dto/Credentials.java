package com.example.userjwtauthwebservice.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Credentials {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
