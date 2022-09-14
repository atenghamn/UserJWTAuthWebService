package com.example.userjwtauthwebservice.auth.domain;

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

    public Integer getId() {
        return id;
    }

    public boolean isAdministrator() {
        return isAdministrator;
    }
}
