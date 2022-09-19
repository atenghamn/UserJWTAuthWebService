package com.example.userjwtauthwebservice.commons;

import com.example.userjwtauthwebservice.auth.domain.JwtUser;
import com.example.userjwtauthwebservice.auth.service.JwtService;
import com.example.userjwtauthwebservice.exception.NotAuthorizedException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static com.example.userjwtauthwebservice.commons.AuthorizationUtil.authorizeAdmin;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AuthorizationUtilTest {

    private static String ADMIN_TOKEN;
    private static String REGULAR_TOKEN;

    @BeforeAll
    public static void setup(){
        var jwtService = new JwtService();

        ADMIN_TOKEN ="Bearer " +  jwtService.encodeJwt(JwtUser.builder()
                    .id(1)
                    .username("Admin")
                    .isAdministrator(true)
                .build());

        REGULAR_TOKEN = "Bearer " + jwtService.encodeJwt(JwtUser.builder()
                        .id(2)
                        .username("Not Admin")
                        .isAdministrator(false)
                .build());
    }

    @Test
    void whenAdmin_thenAuthorize() {
        assertDoesNotThrow(() -> authorizeAdmin(ADMIN_TOKEN, "Authorize admin with admin token"));
    }

    @Test
    void whenNotAdmin_thenDontAuthorize(){
        assertThrows(NotAuthorizedException.class, () -> authorizeAdmin(REGULAR_TOKEN, "Dont authorize admin with non-admin token"));
    }
}
