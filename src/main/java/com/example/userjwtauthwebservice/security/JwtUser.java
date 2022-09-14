package com.example.userjwtauthwebservice.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtUser {
    Integer id;
    String username;
    boolean isAdministrator;
    boolean isLeader;
}
