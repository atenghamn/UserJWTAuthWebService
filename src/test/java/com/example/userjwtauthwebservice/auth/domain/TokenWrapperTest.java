package com.example.userjwtauthwebservice.auth.domain;

import com.example.userjwtauthwebservice.auth.dto.AuthenticationToken;
import com.example.userjwtauthwebservice.auth.service.JwtService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static com.example.userjwtauthwebservice.auth.domain.SecurityConstants.EXPIRATION_TIME;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenWrapperTest {

    private static AuthenticationToken TOKEN;
    @BeforeAll
    public static void setUp(){
        JwtService jwtService = new JwtService();
        TOKEN = AuthenticationToken.builder()
                .accessToken(jwtService.encodeJwt(
                        JwtUser.builder()
                                .id(99)
                                .username("Queen Elisabeth")
                                .isAdministrator(true)
                                .build()))
                .expiresIn(Long.toString(EXPIRATION_TIME))
                .tokenType("Bearer")
                .build();
    }

   @Test
   public void getId(){
        var tokenWrapper = new TokenWrapper("Bearer " + TOKEN.getAccessToken());
        assertEquals(99,  tokenWrapper.getUserId());
   }
}
