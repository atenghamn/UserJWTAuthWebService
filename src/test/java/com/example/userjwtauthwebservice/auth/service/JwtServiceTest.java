package com.example.userjwtauthwebservice.auth.service;

import com.example.userjwtauthwebservice.auth.domain.JwtUser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.example.userjwtauthwebservice.commons.Strings.isNotNullOrEmpty;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JwtServiceTest {

    private static JwtUser user;
    private static JwtService jwtService;

    @BeforeAll
    public static void setUp() {
        jwtService = new JwtService();

        user = JwtUser.builder()
                .id(100)
                .username("Jane Doe")
                .isAdministrator(true)
                .build();
    }

    @Test
    public void encodeJwt_shouldReturnString(){
        assertTrue(isNotNullOrEmpty(jwtService.encodeJwt(user)));
    }

}
